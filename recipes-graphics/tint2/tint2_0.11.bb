DESCRIPTION = "A lightweight panel/taskbar"
SECTION = "x11"
DEPENDS = "cmake-native cairo pango imlib2 glib-2.0"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
HOMEPAGE = "http://code.google.com/p/tint2/"

SRC_URI = "http://tint2.googlecode.com/files/${P}.tar.bz2 \
	file://battery_segfault.patch \
"

SRC_URI[md5sum] = "6fc5731e7425125fa84a2add5cef4bff"
SRC_URI[sha256sum] = "fe106e6a6057d2631abddde9f82d3fd4fb1985c4fb93f10d3886417a9e22471d"

inherit distutils cmake gettext

EXTRA_OECMAKE = " \
    -DWITH_BATTERY=OFF \
    -DWITH_EXAMPLES=OFF \
    -DWITH_TINT2CONF=ON \
"

FILES_${PN} += "${sysconfdir} /usr/share/*"
CONFFILES_${PN} = "${sysconfdir}/xdg/tint2/tint2rc"

