DESCRIPTION = "openbox configuration program"
AUTHOR = "openbox.org"
HOMEPAGE_URL="http://openbox.org/wiki/ObConf:About"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

PR = "r0"

SRC_URI = "http://dev.gentoo.org/~hwoarang/distfiles/${P}_p20111019.tar.gz"

DEPENDS = "openbox gtk+ libglade virtual/gettext libxml2"
RDEPENDS_${PN} = "openbox gtk+"

S = "${WORKDIR}"

inherit pkgconfig autotools

export BUILD_SYS
export HOST_SYS

do_configure_prepend() {
    autopoint || touch config.rpath
    autoreconf -Wcross --verbose --install --force || bbnote "failed to autoreconf"
}

do_configure() {
    export OPENBOX_CFLAGS="-I${STAGING_INCDIR}/openbox/3.5 -I${STAGING_INCDIR}/libxml2"
    export OPENBOX_LIBS="-L${STAGING_LIBDIR} -lobrender -lobt -lSM -lICE -lxml2 -lX11"
    echo $OPENBOX_CFLAGS
    echo $OPENBOX_LIBS

    oe_runconf --sharedstatedir=/usr/share
}

FILES_${PN} += "/usr/share/*"

SRC_URI[md5sum] = "e769bc14b2ee43797c1b1c3acbf06fb2"
SRC_URI[sha256sum] = "c06ba45c99abf4e0c5906266e03486ccbe8391c9aae30498c60be64e5481caa7"
