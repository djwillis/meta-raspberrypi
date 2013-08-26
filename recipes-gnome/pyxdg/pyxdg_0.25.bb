DESCRIPTION = "A Python module to deal with freedesktop.org specifications"
HOMEPAGE = "http://freedesktop.org/wiki/Software/pyxdg"
SECTION = "devel/python"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=f30a9716ef3762e3467a2f62bf790f0a"

PR = "r0"

SRC_URI = "http://people.freedesktop.org/~takluyver/${P}.tar.gz \
"

inherit distutils

PACKAGE_ARCH = "all"

SRC_URI[md5sum] = "bedcdb3a0ed85986d40044c87f23477c"
SRC_URI[sha256sum] = "81e883e0b9517d624e8b0499eb267b82a815c0b7146d5269f364988ae031279d"
