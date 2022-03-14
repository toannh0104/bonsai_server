package com.ascendmoney.ami.report.util;

import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.FetchParent;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.ascendmoney.ami.report.util.MockUtil.mockJoins;
import static com.ascendmoney.ami.report.util.MockUtil.mockPath;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class ServiceVerifyUtil {

    public static <E, R> void verifySearchAll(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository) {
        verifySearchAll(searchMethod, resultClass, repository, true);
    }

    public static <E, R> void verifyEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository,
                                                 Object value, SingularAttribute attribute) {
        verifyEqualsSearch(searchMethod, resultClass, repository, true, value, attribute);
    }

    public static <E, R, T> void verifyEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository,
                                                    Class<T> valueClass, Consumer<T> valueConsumer, SingularAttribute attribute) {
        verifyEqualsSearch(searchMethod, resultClass, repository, true, valueClass, valueConsumer, attribute);
    }

    public static <E, R> void verifyLikeSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository,
                                               String value, SingularAttribute attribute) {
        verifyLikeSearch(searchMethod, resultClass, repository, true, value, attribute);
    }

    public static <E, R> void verifyNullSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository,
                                               SingularAttribute attribute) {
        verifyNullSearch(searchMethod, resultClass, repository, true, attribute);
    }

    public static <E, R> void verifyNotNullSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository,
                                               SingularAttribute attribute) {
        verifyNotNullSearch(searchMethod, resultClass, repository, true, attribute);
    }

    public static <E, R, T> void verifyJoinEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository,
                                                        Class<T> valueClass, Consumer<T> valueConsumer, Attribute... attributes) {
        verifyJoinEqualsSearch(searchMethod, resultClass, repository, true, valueClass, valueConsumer, attributes);
    }

    public static <E, R> void verifyJoinEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository,
                                                     Object value, Attribute... attributes) {
        verifyJoinEqualsSearch(searchMethod, resultClass, repository, true, value, attributes);
    }

    public static <E, R> void verifyJoinLikeSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                     JpaSpecificationExecutor<E> repository, String value, Attribute... attributes) {
        verifyJoinLikeSearch(searchMethod, resultClass, repository, true, value, attributes);
    }

    public static <E, R> void verifyJoinGreaterThanOrEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                     JpaSpecificationExecutor<E> repository, Comparable value, Attribute... attributes) {
        verifyJoinGreaterThanOrEqualsSearch(searchMethod, resultClass, repository, true, value, attributes);
    }

    public static <E, R> void verifyJoinLessThanSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                     JpaSpecificationExecutor<E> repository, Comparable value, Attribute... attributes) {
        verifyJoinLessThanSearch(searchMethod, resultClass, repository, true, value, attributes);
    }

    public static <E, R> void verifyBetweenSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                  JpaSpecificationExecutor<E> repository, Date from, Date to, SingularAttribute attribute) {
        verifyBetweenSearch(searchMethod, resultClass, repository, true, from, to, attribute);
    }

    public static <E, R, T> void verifyInSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository,
                                                Class<T> valueClass, Consumer<List<T>> valueConsumer, SingularAttribute attribute) {
        verifyInSearch(searchMethod, resultClass, repository, true, valueClass, valueConsumer, attribute);
    }

    public static <E, R, T> void verifyJoinInSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository,
                                                    Class<T> valueClass, Consumer<List<T>> valueConsumer, Attribute... attributes) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, true),
                new InJoinSearchConsumeRobot<>(valueClass, valueConsumer, attributes));
    }

    public static <E, R> void verifyGreaterThanOrEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                              JpaSpecificationExecutor<E> repository, Comparable value, SingularAttribute attribute) {
        verifyGreaterThanOrEqualsSearch(searchMethod, resultClass, repository, true, value, attribute);
    }

    public static <E, R> void verifyGreaterThanSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, Comparable value, SingularAttribute attribute) {
        verifyGreaterThanSearch(searchMethod, resultClass, repository, true, value, attribute);
    }

    public static <E, R> void verifyLessThanOrEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                              JpaSpecificationExecutor<E> repository, Comparable value, SingularAttribute attribute) {
        verifyLessThanOrEqualsSearch(searchMethod, resultClass, repository, true, value, attribute);
    }

    public static <E, R> void verifyLessThanSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                           JpaSpecificationExecutor<E> repository, Comparable value, SingularAttribute attribute) {
        verifyLessThanSearch(searchMethod, resultClass, repository, true, value, attribute);
    }

    public static <E, R> Specification<E> verifySearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository) {
        return verifySearch(searchMethod, resultClass, repository, true);
    }

    public static <E, R> void verifySearchAll(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifySearchResult) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult));
    }

    public static <E, R> void verifyEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifySearchResult,
                                                 Object value, SingularAttribute attribute) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new EqualsSearchRobot(value, attribute));
    }

    public static <E, R, T> void verifyEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifySearchResult,
                                                    Class<T> valueClass, Consumer<T> valueConsumer, SingularAttribute attribute) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new EqualsSearchConsumeRobot<>(valueClass, valueConsumer, attribute));
    }

    public static <E, R> void verifyLikeSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifySearchResult,
                                               String value, SingularAttribute attribute) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new LikeSearchRobot(value, attribute));
    }

    public static <E, R> void verifyNullSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifySearchResult,
                                               SingularAttribute attribute) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new NullSearchRobot(attribute));
    }

    public static <E, R> void verifyNotNullSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifySearchResult,
                                                  SingularAttribute attribute) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new NotNullSearchRobot(attribute));
    }

    public static <E, R, T> void verifyJoinEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifySearchResult,
                                                        Class<T> valueClass, Consumer<T> valueConsumer, Attribute... attributes) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new EqualJoinSearchConsumeRobot<>(valueClass, valueConsumer, attributes));
    }

    public static <E, R> void verifyJoinEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifySearchResult,
                                                     Object value, Attribute... attributes) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new EqualJoinSearchRobot(value, attributes));
    }

    public static <E, R> void verifyJoinLikeSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                   JpaSpecificationExecutor<E> repository, boolean verifySearchResult, String value, Attribute... attributes) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new LikeJoinSearchRobot(value, attributes));
    }

    public static <E, R> void verifyJoinGreaterThanOrEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                                  JpaSpecificationExecutor<E> repository, boolean verifySearchResult, Comparable value, Attribute... attributes) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new GreaterThanOrEqualsJoinSearchRobot(value, attributes));
    }

    public static <E, R> void verifyJoinLessThanSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                       JpaSpecificationExecutor<E> repository, boolean verifySearchResult, Comparable value, Attribute... attributes) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new LessThanJoinSearchRobot(value, attributes));
    }

    public static <E, R> void verifyBetweenSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                  JpaSpecificationExecutor<E> repository, boolean verifySearchResult, Date from, Date to, SingularAttribute attribute) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new DateBetweenSearchRobot(from, to, attribute));
    }

    public static <E, R, T> void verifyInSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifySearchResult,
                                                Class<T> valueClass, Consumer<List<T>> valueConsumer, SingularAttribute attribute) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new InSearchConsumeRobot<>(valueClass, valueConsumer, attribute));
    }

    public static <E, R, T> void verifyJoinInSearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifySearchResult,
                                                Class<T> valueClass, Consumer<List<T>> valueConsumer, Attribute... attributes) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new InJoinSearchConsumeRobot<>(valueClass, valueConsumer, attributes));
    }

    public static <E, R> void verifyGreaterThanOrEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                              JpaSpecificationExecutor<E> repository, boolean verifySearchResult, Comparable value, SingularAttribute attribute) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new GreaterThanOrEqualsSearchRobot(value, attribute));
    }

    public static <E, R> void verifyGreaterThanSearch(Supplier<R> searchMethod, Class<R> resultClass,
        JpaSpecificationExecutor<E> repository, boolean verifySearchResult, Comparable value, SingularAttribute attribute) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
            new GreaterThanSearchRobot(value, attribute));
    }

    public static <E, R> void verifyLessThanOrEqualsSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                           JpaSpecificationExecutor<E> repository, boolean verifySearchResult, Comparable value, SingularAttribute attribute) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new LessThanOrEqualsSearchRobot(value, attribute));
    }

    public static <E, R> void verifyLessThanSearch(Supplier<R> searchMethod, Class<R> resultClass,
                                                   JpaSpecificationExecutor<E> repository, boolean verifySearchResult, Comparable value, SingularAttribute attribute) {
        verifySpecification(verifySearch(searchMethod, resultClass, repository, verifySearchResult),
                new LessThanSearchRobot(value, attribute));
    }

    public static <E, R> Specification<E> verifySearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifyResult) {
        return verifySearch(searchMethod, resultClass, repository, verifyResult, null);
    }

    public static <E, R> Specification<E> verifySearch(Supplier<R> searchMethod, Class<R> resultClass, JpaSpecificationExecutor<E> repository, boolean verifyResult, Consumer<R> resultConsumer) {
        R resultMock = mock(resultClass);
        if(resultConsumer != null) {
            resultConsumer.accept(resultMock);
        }
        List<Boolean> isUseSort = new ArrayList<>();
        if (Page.class.isAssignableFrom(resultClass)) {
            Page pageMock = (Page) resultMock;
            lenient().when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(pageMock);
            Pageable pageableMock = mock(Pageable.class);
            lenient().when(pageMock.getPageable()).thenReturn(pageableMock);
        }
        else if (List.class.isAssignableFrom(resultClass)) {
            lenient().when(repository.findAll(any(Specification.class), any(Sort.class))).thenAnswer((Answer<List>) invocation -> {
                isUseSort.add(true);
                return (List)resultMock;
            });
            lenient().when(repository.findAll(any(Specification.class))).thenAnswer((Answer<List>) invocation -> {
                isUseSort.add(false);
                return (List)resultMock;
            });
        }

        R result = searchMethod.get();

        if(verifyResult) {
            assertEquals(resultMock, result);
        }
        ArgumentCaptor<Specification> specificationArgumentCaptor = ArgumentCaptor.forClass(Specification.class);
        if (Page.class.isAssignableFrom(resultClass))
            verify(repository, times(1)).findAll(specificationArgumentCaptor.capture(), any(Pageable.class));
        else if (List.class.isAssignableFrom(resultClass)) {
            if(isUseSort.get(0) ) {
                verify(repository, times(1)).findAll(specificationArgumentCaptor.capture(), any(Sort.class));
            }
            else {
                verify(repository, times(1)).findAll(specificationArgumentCaptor.capture());
            }
        }
        return specificationArgumentCaptor.getValue();
    }

    public static <E> Specification<E> verifySearchWithoutSort(Supplier<List> searchMethod, JpaSpecificationExecutor<E> repository) {
        List resultMock = mock(List.class);
        lenient().when(repository.findAll(any(Specification.class))).thenReturn(resultMock);

        List result = searchMethod.get();

        assertEquals(resultMock, result);
        ArgumentCaptor<Specification> specificationArgumentCaptor = ArgumentCaptor.forClass(Specification.class);
        verify(repository, times(1)).findAll(specificationArgumentCaptor.capture());
        return specificationArgumentCaptor.getValue();
    }

    public static <E> void verifySpecification(Specification<E> specification, Robot... robots) {
        verifySpecification(specification, null, robots);
    }

    public static <E> void verifySpecification(Specification<E> specification, Class<E> resultClass, Robot... robots) {
        Root<E> rootMock = mockRoot();
        CriteriaQuery<E> criteriaQueryMock = mock(CriteriaQuery.class);
        CriteriaBuilder criteriaBuilderMock = mock(CriteriaBuilder.class);
        Predicate predicateMock = mock(Predicate.class);
        lenient().when(criteriaQueryMock.getResultType()).thenReturn(resultClass);

        List<Predicate> predicates = Arrays.stream(robots)
                .map(robot -> robot.mockPredicate(rootMock, criteriaQueryMock, criteriaBuilderMock))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        lenient().when(criteriaBuilderMock.and(any())).thenReturn(predicateMock);

        Predicate predicate = specification.toPredicate(rootMock, criteriaQueryMock, criteriaBuilderMock);
        assertEquals(predicateMock, predicate);

        for(Robot robot : robots) {
            robot.verifyPredicate(rootMock, criteriaQueryMock, criteriaBuilderMock);
        }

        ArgumentCaptor<Predicate> captor = ArgumentCaptor.forClass(Predicate.class);
        verify(criteriaBuilderMock, times(1)).and(captor.capture());
        ListVerifyUtil.assertListEqualsWithoutOrder(predicates, captor.getAllValues());
    }

    public static Root mockRoot() {
        Root rootMock = mock(Root.class);
        Fetch fetchMock = mock(MockUtil.JoinFetch.class);
        stubFetch(rootMock, fetchMock);
        stubFetch(fetchMock, fetchMock);
        return rootMock;
    }

    private static void stubFetch(FetchParent fetchParent, Fetch fetchMock) {
        lenient().when(fetchParent.fetch(any(SingularAttribute.class), any(JoinType.class))).thenReturn(fetchMock);
        lenient().when(fetchParent.fetch(any(PluralAttribute.class), any(JoinType.class))).thenReturn(fetchMock);
        lenient().when(fetchParent.fetch(any(String.class), any(JoinType.class))).thenReturn(fetchMock);
        lenient().when(fetchParent.fetch(any(SingularAttribute.class))).thenReturn(fetchMock);
        lenient().when(fetchParent.fetch(any(PluralAttribute.class))).thenReturn(fetchMock);
        lenient().when(fetchParent.fetch(any(String.class))).thenReturn(fetchMock);
    }

    public interface Robot {

        Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder);

        void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder);
    }

    public static abstract class MockRobot implements Robot {

        abstract public void mock(Root root, CriteriaQuery query, CriteriaBuilder builder);

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            mock(root, query, builder);
            return null;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder){}
    }

    public static class EqualsSearchRobot implements Robot {

        private Object value;
        private SingularAttribute attribute;

        private From from;
        private Path pathMock;

        public EqualsSearchRobot(Object value, SingularAttribute attribute) {
            this(null, null, value, attribute);
        }

        public EqualsSearchRobot(From from, Path path, Object value, SingularAttribute attribute) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.value = value;
            this.attribute = attribute;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            lenient().when(from.get(attribute)).thenReturn(pathMock);
            lenient().when(builder.equal(pathMock, value)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).equal(pathMock, value);
        }
    }

    public static class EqualsSearchConsumeRobot<T> implements Robot {

        private Class<T> valueClass;
        private Consumer<T> valueConsumer;
        private SingularAttribute attribute;

        private From from;
        private Path pathMock;

        public EqualsSearchConsumeRobot(Class<T> valueClass, Consumer<T> valueConsumer, SingularAttribute attribute) {
            this(null, null, valueClass, valueConsumer, attribute);
        }

        public EqualsSearchConsumeRobot(From from, Path path, Class<T> valueClass, Consumer<T> valueConsumer, SingularAttribute attribute) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.valueClass = valueClass;
            this.valueConsumer = valueConsumer;
            this.attribute = attribute;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            lenient().when(from.get(attribute)).thenReturn(pathMock);
            lenient().when(builder.equal(eq(pathMock), any(valueClass))).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            ArgumentCaptor<T> valueCaptor = ArgumentCaptor.forClass(valueClass);
            verify(builder, times(1)).equal(eq(pathMock), valueCaptor.capture());
            valueConsumer.accept(valueCaptor.getValue());
        }
    }

    public static class LikeSearchRobot implements Robot {

        private String value;
        private SingularAttribute attribute;

        private From from;
        private Path pathMock;

        public LikeSearchRobot(String value, SingularAttribute attribute) {
            this(null, null, value, attribute);
        }

        public LikeSearchRobot(From from, Path path, String value, SingularAttribute attribute) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.value = value;
            this.attribute = attribute;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            lenient().when(from.get(attribute)).thenReturn(pathMock);
            lenient().when(builder.like(pathMock, value)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).like(pathMock, value);
        }
    }

    public static class NullSearchRobot implements Robot {

        private SingularAttribute attribute;

        private From from;
        private Path pathMock;

        public NullSearchRobot(SingularAttribute attribute) {
            this(null, null, attribute);
        }

        public NullSearchRobot(From from, Path path, SingularAttribute attribute) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.attribute = attribute;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            lenient().when(from.get(attribute)).thenReturn(pathMock);
            lenient().when(builder.isNull(pathMock)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).isNull(pathMock);
        }
    }

    public static class NotNullSearchRobot implements Robot {

        private SingularAttribute attribute;

        private From from;
        private Path pathMock;

        public NotNullSearchRobot(SingularAttribute attribute) {
            this(null, null, attribute);
        }

        public NotNullSearchRobot(From from, Path path, SingularAttribute attribute) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.attribute = attribute;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            lenient().when(from.get(attribute)).thenReturn(pathMock);
            lenient().when(builder.isNotNull(pathMock)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).isNotNull(pathMock);
        }
    }

    public static class EqualJoinSearchRobot implements Robot {

        private Object value;
        private Attribute[] attributes;

        private From from;
        private Path pathMock;

        public EqualJoinSearchRobot(Object value, Attribute... attributes) {
            this(null, null, value, attributes);
        }

        public EqualJoinSearchRobot(From from, Path path, Object value, Attribute... attributes) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.value = value;
            this.attributes = attributes;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            Attribute[] joinAttributes = Arrays.copyOfRange(attributes, 0, attributes.length - 1);
            Join joinMock = mockJoins(from, null, joinAttributes);
            mockPath(joinMock, attributes[attributes.length - 1], pathMock);
            lenient().when(builder.equal(pathMock, value)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).equal(pathMock, value);
        }
    }

    public static class NullJoinSearchRobot implements Robot {

        private Attribute[] attributes;

        private From from;
        private Path pathMock;

        public NullJoinSearchRobot(Attribute... attributes) {
            this(null, null, attributes);
        }

        public NullJoinSearchRobot(From from, Path path, Attribute... attributes) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.attributes = attributes;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            Attribute[] joinAttributes = Arrays.copyOfRange(attributes, 0, attributes.length - 1);
            Join joinMock = mockJoins(from, null, joinAttributes);
            mockPath(joinMock, attributes[attributes.length - 1], pathMock);
            lenient().when(builder.isNull(pathMock)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).isNull(pathMock);
        }
    }

    public static class EqualJoinSearchConsumeRobot<T> implements Robot {

        private Class<T> valueClass;
        private Consumer<T> valueConsumer;
        private Attribute[] attributes;

        private From from;
        private Path pathMock;

        public EqualJoinSearchConsumeRobot(Class<T> valueClass, Consumer<T> valueConsumer, Attribute... attributes) {
            this(null, null, valueClass, valueConsumer, attributes);
        }

        public EqualJoinSearchConsumeRobot(From from, Path path, Class<T> valueClass, Consumer<T> valueConsumer, Attribute... attributes) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.valueClass = valueClass;
            this.valueConsumer = valueConsumer;
            this.attributes = attributes;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            Attribute[] joinAttributes = Arrays.copyOfRange(attributes, 0, attributes.length - 1);
            Join joinMock = mockJoins(from, null, joinAttributes);
            mockPath(joinMock, attributes[attributes.length - 1], pathMock);
            lenient().when(builder.equal(eq(pathMock), any(valueClass))).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            ArgumentCaptor<T> valueCaptor = ArgumentCaptor.forClass(valueClass);
            verify(builder, times(1)).equal(eq(pathMock), valueCaptor.capture());
            valueConsumer.accept(valueCaptor.getValue());
        }
    }

    public static class LikeJoinSearchRobot implements Robot {

        private String value;
        private Attribute[] attributes;

        private From from;
        private Path pathMock;

        public LikeJoinSearchRobot(String value, Attribute... attributes) {
            this(null, null, value, attributes);
        }

        public LikeJoinSearchRobot(From from, Path path, String value, Attribute... attributes) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.value = value;
            this.attributes = attributes;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            Attribute[] joinAttributes = Arrays.copyOfRange(attributes, 0, attributes.length - 1);
            Join joinMock = mockJoins(from, null, joinAttributes);
            mockPath(joinMock, attributes[attributes.length - 1], pathMock);
            lenient().when(builder.like(pathMock, value)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).like(pathMock, value);
        }
    }

    public static class DateBetweenSearchRobot implements Robot {

        private Date fromDate;
        private Date toDate;
        private SingularAttribute attribute;

        private From from;
        private Path pathMock;

        public DateBetweenSearchRobot(Date fromDate, Date toDate, SingularAttribute attribute) {
            this(null, null, fromDate, toDate, attribute);
        }

        public DateBetweenSearchRobot(From from, Path path, Date fromDate, Date toDate, SingularAttribute attribute) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.attribute = attribute;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            lenient().when(from.get(attribute)).thenReturn(pathMock);
            lenient().when(builder.between(pathMock, fromDate, toDate)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).between(pathMock, fromDate, toDate);
        }
    }

    public static class InSearchConsumeRobot<T> implements Robot {

        private Class<T> valueClass;
        private Consumer<List<T>> valueConsumer;
        private SingularAttribute attribute;

        private From from;
        private Path pathMock;
        private CriteriaBuilder.In inMock = mock(CriteriaBuilder.In.class);

        public InSearchConsumeRobot(Class<T> valueClass, Consumer<List<T>> valueConsumer, SingularAttribute attribute) {
            this(null, null, valueClass, valueConsumer, attribute);
        }

        public InSearchConsumeRobot(From from, Path path, Class<T> valueClass, Consumer<List<T>> valueConsumer, SingularAttribute attribute) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.valueClass = valueClass;
            this.valueConsumer = valueConsumer;
            this.attribute = attribute;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            From from = this.from != null ? this.from : root;
            lenient().when(from.get(attribute)).thenReturn(pathMock);
            lenient().when(builder.in(pathMock)).thenReturn(inMock);
            lenient().when(inMock.value(any(valueClass))).thenReturn(inMock);
            lenient().when(pathMock.in(anyList())).thenReturn(inMock);
            lenient().when(builder.or(any())).thenReturn(inMock);
            return inMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            if(!mockingDetails(builder.in(pathMock)).getInvocations().isEmpty()) {
                ArgumentCaptor<T> valueCaptor = ArgumentCaptor.forClass(valueClass);
                verify(inMock).value(valueCaptor.capture());
                valueConsumer.accept(valueCaptor.getAllValues());
            }
            else {
                ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
                verify(pathMock).in(valueCaptor.capture());
                valueConsumer.accept(valueCaptor.getValue());
            }
        }
    }

    public static class InJoinSearchConsumeRobot<T> implements Robot {

        private Class<T> valueClass;
        private Consumer<List<T>> valueConsumer;
        private Attribute[] attributes;

        private From from;
        private Path pathMock;
        private CriteriaBuilder.In inMock = mock(CriteriaBuilder.In.class);

        public InJoinSearchConsumeRobot(Class<T> valueClass, Consumer<List<T>> valueConsumer, Attribute... attributes) {
            this(null, null, valueClass, valueConsumer, attributes);
        }

        public InJoinSearchConsumeRobot(From from, Path path, Class<T> valueClass, Consumer<List<T>> valueConsumer, Attribute... attributes) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.valueClass = valueClass;
            this.valueConsumer = valueConsumer;
            this.attributes = attributes;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            From from = this.from != null ? this.from : root;
            Attribute[] joinAttributes = Arrays.copyOfRange(attributes, 0, attributes.length - 1);
            Join joinMock = mockJoins(from, null, joinAttributes);
            mockPath(joinMock, attributes[attributes.length - 1], pathMock);
            lenient().when(builder.in(pathMock)).thenReturn(inMock);
            lenient().when(inMock.value(any(valueClass))).thenReturn(inMock);
            lenient().when(pathMock.in(anyList())).thenReturn(inMock);
            return inMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            if(!mockingDetails(builder.in(pathMock)).getInvocations().isEmpty()) {
                ArgumentCaptor<T> valueCaptor = ArgumentCaptor.forClass(valueClass);
                verify(inMock).value(valueCaptor.capture());
                valueConsumer.accept(valueCaptor.getAllValues());
            }
            else {
                ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
                verify(pathMock).in(valueCaptor.capture());
                valueConsumer.accept(valueCaptor.getValue());
            }
        }
    }

    public static class InSearchOrConsumeRobot implements Robot {

        private Class valueClass;
        private Consumer<List> valueConsumer;
        private SingularAttribute[] attributes;

        private From from;
        private Path pathMock;
        private CriteriaBuilder.In inMock = mock(CriteriaBuilder.In.class);

        public InSearchOrConsumeRobot(Class valueClass, Consumer<List> valueConsumer, SingularAttribute ... attributes) {
            this(null, null, valueClass, valueConsumer, attributes);
        }

        public InSearchOrConsumeRobot(From from, Path path, Class valueClass, Consumer<List> valueConsumer, SingularAttribute ... attributes) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.valueClass = valueClass;
            this.valueConsumer = valueConsumer;
            this.attributes = attributes;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            From from = this.from != null ? this.from : root;
            Attribute[] joinAttributes = Arrays.copyOfRange(attributes, 0, attributes.length - 1);
            Join joinMock = mockJoins(from, null, joinAttributes);

            for(SingularAttribute attribute : attributes) {
                lenient().when(from.get(attribute)).thenReturn(pathMock);
                mockPath(joinMock, attribute, pathMock);
            }

            lenient().when(builder.in(pathMock)).thenReturn(inMock);
            lenient().when(inMock.value(any(valueClass))).thenReturn(inMock);
            lenient().when(pathMock.in(anyList())).thenReturn(inMock);
            lenient().when(builder.or(any())).thenReturn(inMock);

            return inMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            if(!mockingDetails(builder.in(pathMock)).getInvocations().isEmpty()) {
                ArgumentCaptor valueCaptor = ArgumentCaptor.forClass(valueClass);
                verify(inMock).value(valueCaptor.capture());
                valueConsumer.accept(valueCaptor.getAllValues());
            }
            else {
                ArgumentCaptor<List> valueCaptor = ArgumentCaptor.forClass(List.class);
                verify(pathMock, times(attributes.length)).in(valueCaptor.capture());
                valueConsumer.accept(valueCaptor.getValue());
            }
        }
    }

    public static class GreaterThanOrEqualsSearchRobot implements Robot {

        private Comparable value;
        private SingularAttribute attribute;

        private From from;
        private Path pathMock;

        public GreaterThanOrEqualsSearchRobot(Comparable value, SingularAttribute attribute) {
            this(null, null, value, attribute);
        }

        public GreaterThanOrEqualsSearchRobot(From from, Path path, Comparable value, SingularAttribute attribute) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.value = value;
            this.attribute = attribute;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            lenient().when(from.get(attribute)).thenReturn(pathMock);
            lenient().when(builder.greaterThanOrEqualTo(pathMock, value)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).greaterThanOrEqualTo(pathMock, value);
        }
    }

    public static class LessThanOrEqualsSearchRobot implements Robot {

        private Comparable value;
        private SingularAttribute attribute;

        private From from;
        private Path pathMock;

        public LessThanOrEqualsSearchRobot(Comparable value, SingularAttribute attribute) {
            this(null, null, value, attribute);
        }

        public LessThanOrEqualsSearchRobot(From from, Path path, Comparable value, SingularAttribute attribute) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.value = value;
            this.attribute = attribute;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            lenient().when(from.get(attribute)).thenReturn(pathMock);
            lenient().when(builder.lessThanOrEqualTo(pathMock, value)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).lessThanOrEqualTo(pathMock, value);
        }
    }

    public static class LessThanSearchRobot implements Robot {

        private Comparable value;
        private SingularAttribute attribute;

        private From from;
        private Path pathMock;

        public LessThanSearchRobot(Comparable value, SingularAttribute attribute) {
            this(null, null, value, attribute);
        }

        public LessThanSearchRobot(From from, Path path, Comparable value, SingularAttribute attribute) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.value = value;
            this.attribute = attribute;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            lenient().when(from.get(attribute)).thenReturn(pathMock);
            lenient().when(builder.lessThan(pathMock, value)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).lessThan(pathMock, value);
        }
    }

    public static class GreaterThanSearchRobot implements Robot {

        private Comparable value;
        private SingularAttribute attribute;

        private From from;
        private Path pathMock;

        public GreaterThanSearchRobot(Comparable value, SingularAttribute attribute) {
            this(null, null, value, attribute);
        }

        public GreaterThanSearchRobot(From from, Path path, Comparable value, SingularAttribute attribute) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.value = value;
            this.attribute = attribute;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            lenient().when(from.get(attribute)).thenReturn(pathMock);
            lenient().when(builder.greaterThan(pathMock, value)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).greaterThan(pathMock, value);
        }
    }

    public static class GreaterThanOrEqualsJoinSearchRobot implements Robot {

        private Comparable value;
        private Attribute[] attributes;

        private From from;
        private Path pathMock;

        public GreaterThanOrEqualsJoinSearchRobot(Comparable value, Attribute... attributes) {
            this(null, null, value, attributes);
        }

        public GreaterThanOrEqualsJoinSearchRobot(From from, Path path, Comparable value, Attribute... attributes) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.value = value;
            this.attributes = attributes;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            Attribute[] joinAttributes = Arrays.copyOfRange(attributes, 0, attributes.length - 1);
            Join joinMock = mockJoins(from, null, joinAttributes);
            mockPath(joinMock, attributes[attributes.length - 1], pathMock);
            lenient().when(builder.greaterThanOrEqualTo(pathMock, value)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).greaterThanOrEqualTo(pathMock, value);
        }
    }

    public static class LessThanJoinSearchRobot implements Robot {

        private Comparable value;
        private Attribute[] attributes;

        private From from;
        private Path pathMock;

        public LessThanJoinSearchRobot(Comparable value, Attribute... attributes) {
            this(null, null, value, attributes);
        }

        public LessThanJoinSearchRobot(From from, Path path, Comparable value, Attribute... attributes) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.value = value;
            this.attributes = attributes;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            Attribute[] joinAttributes = Arrays.copyOfRange(attributes, 0, attributes.length - 1);
            Join joinMock = mockJoins(from, null, joinAttributes);
            mockPath(joinMock, attributes[attributes.length - 1], pathMock);
            lenient().when(builder.lessThan(pathMock, value)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).lessThan(pathMock, value);
        }
    }

    public static class LessThanOrEqualsJoinSearchRobot implements Robot {

        private Comparable value;
        private Attribute[] attributes;

        private From from;
        private Path pathMock;

        public LessThanOrEqualsJoinSearchRobot(Comparable value, Attribute... attributes) {
            this(null, null, value, attributes);
        }

        public LessThanOrEqualsJoinSearchRobot(From from, Path path, Comparable value, Attribute... attributes) {
            this.from = from;
            this.pathMock = path != null ? path : mock(Path.class);
            this.value = value;
            this.attributes = attributes;
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            Predicate predicateMock = mock(Predicate.class);
            From from = this.from != null ? this.from : root;
            Attribute[] joinAttributes = Arrays.copyOfRange(attributes, 0, attributes.length - 1);
            Join joinMock = mockJoins(from, null, joinAttributes);
            mockPath(joinMock, attributes[attributes.length - 1], pathMock);
            lenient().when(builder.lessThanOrEqualTo(pathMock, value)).thenReturn(predicateMock);
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            verify(builder, times(1)).lessThanOrEqualTo(pathMock, value);
        }
    }

    public static class OrSearchRobot implements Robot {

        private List<Robot> robots;
        private Predicate predicateMock = mock(Predicate.class);
        Predicate[] predicates;

        public OrSearchRobot(Robot... robots) {
            this.robots = Arrays.asList(robots);
        }

        @Override
        public Predicate mockPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            predicates = robots.stream()
                    .map(robot -> robot.mockPredicate(root, query, builder))
                    .toArray(Predicate[]::new);
            if(predicates.length == 2) {
                when(builder.or(any(), any())).thenReturn(predicateMock);
            }
            else {
                when(builder.or(any())).thenReturn(predicateMock);
            }
            return predicateMock;
        }

        @Override
        public void verifyPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
            robots.forEach(robot -> robot.verifyPredicate(root, query, builder));
            ArgumentCaptor<Predicate> predicateArgumentCaptor = ArgumentCaptor.forClass(Predicate.class);
            if(predicates.length == 2) {
                verify(builder, times(1)).or(predicateArgumentCaptor.capture(), predicateArgumentCaptor.capture());
            }
            else {
                verify(builder, times(1)).or(predicateArgumentCaptor.capture());
            }
            ListVerifyUtil.assertListEqualsWithoutOrder(Arrays.asList(predicates), predicateArgumentCaptor.getAllValues());
        }
    }

}
