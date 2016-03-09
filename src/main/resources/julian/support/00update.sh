#!/bin/sh
#
# usage: ./support/00update.sh /some/where/julius-*.*-linuxbin.tar.gz
#
# update win32bin package from linuxbin of the same version
#
# should be invoked at the parent directory.
#
# you should put windows binaries in bin/ manually!
#

# expand linux binary package
rm -rf linuxbin julius-*-linuxbin
tar xzvf $1
ln -s julius-*-linuxbin linuxbin

# update doc
rm -rf doc
cp -p linuxbin/00readme-julius.txt .
cp -p linuxbin/00readme-julius-ja.txt .
cp -p linuxbin/LICENSE.txt .
cp -p linuxbin/Release* .
cp -p linuxbin/Sample* .
cp -p linuxbin/*.pdf .

# update sample code
rsync -avz linuxbin/julius-simple .

# man2txt
mkdir -p manuals
for i in linuxbin/man/*.1; do
    f=`basename $i .1`
    nroff -man ${i} | sed -e 's/.//g' > manuals/${f}.txt
done
mkdir -p manuals-ja
for i in linuxbin/man/ja/*.1; do
    f=`basename $i .1`
    LANG=ja_JP.eucJP nroff -Tnippon -man ${i} | sed -e 's/.//g' > manuals-ja/${f}.txt
done

# convert character set
qkc -s -m 00readme-julius-ja.txt 00readme-julius.txt LICENSE.txt Release* Sample*
find manuals -name '*.txt' -exec qkc -s -m {} \;
find manuals-ja -name '*.txt' -exec qkc -s -m {} \;
find include -name '*.txt' -exec qkc -s -m {} \;
find include -name '*.h' -exec qkc -s -m {} \;
find lib -name '*.txt' -exec qkc -s -m {} \;
qkc -s -m julius-simple/*

# end
rm -rf linuxbin julius-*-linuxbin
echo 'You should update documents:'
echo '    00readme.txt 00readme-ja.txt'
echo 'You sohuld replace these with win32-compiled ones'
echo '    bin, include, lib'
echo 'Also execute "./support/setprefix.pl .." to bin/lib{sent|julius}-config'
echo 'Also update "plugin-sample" and modify Makefile.'
