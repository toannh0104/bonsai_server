package com.ascendmoney.ami.report.util;

import com.ascendmoney.ami.report.utility.ThrowingConsumer;

import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockUtil {

    public static void mockAllAttributes(Class clazz) {
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> Attribute.class.isAssignableFrom(field.getType()))
                .forEach((ThrowingConsumer<Field>) MockUtil::mockAttributeField);
    }

    public static List<Path> mockPaths(Root root, Attribute... attributes) {
        List<Path> paths = new ArrayList<>();
        for (Attribute attribute : attributes) {
            Path pathMock = mock(Path.class);
            if (attribute instanceof SingularAttribute) {
                when(root.get((SingularAttribute) attribute)).thenReturn(pathMock);
            } else if (attribute instanceof MapAttribute) {
                when(root.get((MapAttribute) attribute)).thenReturn(pathMock);
            } else if (attribute instanceof ListAttribute) {
                when(root.get((ListAttribute) attribute)).thenReturn(pathMock);
            }
            paths.add(pathMock);
        }
        return paths;
    }

    public static void addSameHashKeys(Map<String, Object> map, List<String> keys) {
        keys.forEach(key -> map.put("\0" + key, "\0"));
        map.put("\0", null);
    }

    public static void addSameHashKeys(Map<String, Object> map, String... keys) {
        for(String key : keys) {
            map.put("\0" + key, "\0");
        }
        map.put("\0", null);
    }

    private static void mockAttributeField(Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Attribute mock = null;
        if(field.getType().equals(SingularAttribute.class)) {
            mock = mock(SingularAttribute.class);
        }
        if(field.getType().equals(SetAttribute.class)) {
            mock = mock(SetAttribute.class);
        }
        if(field.getType().equals(CollectionAttribute.class)) {
            mock = mock(SingularAttribute.class);
        }
        if(field.getType().equals(ListAttribute.class)) {
            mock = mock(ListAttribute.class);
        }
        if(field.getType().equals(MapAttribute.class)) {
            mock = mock(MapAttribute.class);
        }

        lenient().when(mock.getName()).thenReturn(field.getName());
        field.set(null, mock);
    }

    public static Join mockJoin(From from, Join join, Attribute attribute) {
        if (attribute instanceof SingularAttribute) {
            Join joinMock = join != null ? join : mock(JoinFetch.class);
            lenient().when(from.join((SingularAttribute) attribute)).thenReturn(joinMock);
            lenient().when(from.join(eq((SingularAttribute) attribute), any(JoinType.class))).thenReturn(joinMock);
            if(Fetch.class.isAssignableFrom(joinMock.getClass())) {
                lenient().when(from.fetch((SingularAttribute) attribute)).thenReturn((Fetch)joinMock);
                lenient().when(from.fetch(eq((SingularAttribute) attribute), any(JoinType.class))).thenReturn((Fetch)joinMock);
            }
            return joinMock;
        }
        if (attribute instanceof SetAttribute) {
            SetJoin joinMock = join != null ? (SetJoin)join : mock(SetJoinFetch.class);
            lenient().when(from.join((SetAttribute) attribute)).thenReturn(joinMock);
            lenient().when(from.join(eq((SetAttribute) attribute), any(JoinType.class))).thenReturn(joinMock);
            if(Fetch.class.isAssignableFrom(joinMock.getClass())) {
                lenient().when(from.fetch((SetAttribute) attribute)).thenReturn((Fetch)joinMock);
                lenient().when(from.fetch(eq((SetAttribute) attribute), any(JoinType.class))).thenReturn((Fetch)joinMock);
            }
            return joinMock;
        }
        if (attribute instanceof CollectionAttribute) {
            CollectionJoin joinMock = join != null ? (CollectionJoin)join : mock(CollectionJoinFetch.class);
            lenient().when(from.join((CollectionAttribute) attribute)).thenReturn(joinMock);
            lenient().when(from.join(eq((CollectionAttribute) attribute), any(JoinType.class))).thenReturn(joinMock);
            if(Fetch.class.isAssignableFrom(joinMock.getClass())) {
                lenient().when(from.fetch((CollectionAttribute) attribute)).thenReturn((Fetch)joinMock);
                lenient().when(from.fetch(eq((CollectionAttribute) attribute), any(JoinType.class))).thenReturn((Fetch)joinMock);
            }
            return joinMock;
        }
        if (attribute instanceof ListAttribute) {
            ListJoin joinMock = join != null ? (ListJoin)join : mock(ListJoinFetch.class);
            lenient().when(from.join((ListAttribute) attribute)).thenReturn(joinMock);
            lenient().when(from.join(eq((ListAttribute) attribute), any(JoinType.class))).thenReturn(joinMock);
            if(Fetch.class.isAssignableFrom(joinMock.getClass())) {
                lenient().when(from.fetch((ListAttribute) attribute)).thenReturn((Fetch)joinMock);
                lenient().when(from.fetch(eq((ListAttribute) attribute), any(JoinType.class))).thenReturn((Fetch)joinMock);
            }
            return joinMock;
        }
        if (attribute instanceof MapAttribute) {
            MapJoin joinMock = join != null ? (MapJoin)join : mock(MapJoinFetch.class);
            lenient().when(from.join((MapAttribute) attribute)).thenReturn(joinMock);
            lenient().when(from.join(eq((MapAttribute) attribute), any(JoinType.class))).thenReturn(joinMock);
            if(Fetch.class.isAssignableFrom(joinMock.getClass())) {
                lenient().when(from.fetch((MapAttribute) attribute)).thenReturn((Fetch)joinMock);
                lenient().when(from.fetch(eq((MapAttribute) attribute), any(JoinType.class))).thenReturn((Fetch)joinMock);
            }
            return joinMock;
        }
        return null;
    }

    public static Join mockJoins(From from, Join destJoin, Attribute... attributes) {
        int startIndex = 0;
        Join joinMock = null;
        if(attributes.length > 0) {
            if(attributes[startIndex] == null)
                throw new RuntimeException("Attribute at index " + startIndex + " is not mocked yet");
            joinMock = mockJoin(from, startIndex < attributes.length - 1 ? null : destJoin, attributes[startIndex]);
            startIndex++;
        }
        for (int i = startIndex; i < attributes.length; i++) {
            if(attributes[i] == null)
                throw new RuntimeException("Attribute at index " + i + " is not mocked yet");
            joinMock = mockJoin(joinMock, i < attributes.length - 1 ? null : destJoin, attributes[i]);
        }
        return joinMock;
    }

    public static Fetch mockFetch(From from, Fetch fetch, Attribute attribute) {
        if (attribute instanceof SingularAttribute) {
            Fetch fetchMock = fetch != null ? fetch : mock(JoinFetch.class);
            lenient().when(from.fetch((SingularAttribute) attribute)).thenReturn(fetchMock);
            lenient().when(from.fetch(eq((SingularAttribute) attribute), any(JoinType.class))).thenReturn(fetchMock);
            return fetchMock;
        }
        if (attribute instanceof SetAttribute) {
            Fetch fetchMock = fetch != null ? fetch : mock(SetJoinFetch.class);
            lenient().when(from.fetch((SetAttribute) attribute)).thenReturn(fetchMock);
            lenient().when(from.fetch(eq((SetAttribute) attribute), any(JoinType.class))).thenReturn(fetchMock);
            return fetchMock;
        }
        if (attribute instanceof CollectionAttribute) {
            Fetch fetchMock = fetch != null ? fetch : mock(CollectionJoinFetch.class);
            lenient().when(from.fetch((CollectionAttribute) attribute)).thenReturn(fetchMock);
            lenient().when(from.fetch(eq((CollectionAttribute) attribute), any(JoinType.class))).thenReturn(fetchMock);
            return fetchMock;
        }
        if (attribute instanceof ListAttribute) {
            Fetch fetchMock = fetch != null ? fetch : mock(ListJoinFetch.class);
            lenient().when(from.fetch((ListAttribute) attribute)).thenReturn(fetchMock);
            lenient().when(from.fetch(eq((ListAttribute) attribute), any(JoinType.class))).thenReturn(fetchMock);
            return fetchMock;
        }
        if (attribute instanceof MapAttribute) {
            Fetch fetchMock = fetch != null ? fetch : mock(MapJoinFetch.class);
            lenient().when(from.fetch((MapAttribute) attribute)).thenReturn(fetchMock);
            lenient().when(from.fetch(eq((MapAttribute) attribute), any(JoinType.class))).thenReturn(fetchMock);
            return fetchMock;
        }
        return null;
    }

    public static void mockPath(From from, Attribute attribute, Path path) {
        if (attribute instanceof SingularAttribute) {
            lenient().when(from.get((SingularAttribute) attribute)).thenReturn(path);
        }
        if (attribute instanceof MapAttribute) {
            lenient().when(from.get((MapAttribute) attribute)).thenReturn(path);
        }
        if (attribute instanceof PluralAttribute) {
            lenient().when(from.get((PluralAttribute) attribute)).thenReturn(path);
        }
    }

    public interface JoinFetch extends Join, Fetch{}

    public interface ListJoinFetch extends Join, ListJoin, Fetch{}

    public interface SetJoinFetch extends Join, SetJoin, Fetch{}

    public interface MapJoinFetch extends Join, MapJoin, Fetch{}

    public interface CollectionJoinFetch extends Join, CollectionJoin, Fetch{}
}
