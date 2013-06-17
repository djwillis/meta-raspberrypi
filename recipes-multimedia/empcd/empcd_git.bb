DESCRIPTION = "EMPCd is the Event Music Player Client daemon"
HOMEPAGE = "http://unfix.org/projects/empcd/"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0b7766af385b099ac9437fa883a2d279"

DEPENDS = "mpd"

inherit update-rc.d

SRCREV = "${AUTOREV}"
PV = "2013+gitr${SRCPV}"
PR = "r1"
BRANCH = "meta-raspberrypi"

SRC_URI = "git://git@github.com/sarnold/empcd.git;protocol=http;branch=${BRANCH} \
           file://empcd.init \
           file://empcd-ipazzport.conf \
"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "'CC=${CC}' 'CXX=${CXX}'"

LDFLAGS_append = "${@bb.utils.contains('TUNE_FEATURES', 'vfp', '-mfloat-abi=hard', '', d)}"

do_install() {
    oe_runmake install DESTDIR=${D}

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/empcd.init ${D}${sysconfdir}/init.d/empcd
    install -m 0755 ${WORKDIR}/empcd-ipazzport.conf ${D}${sysconfdir}/empcd.conf
}

INITSCRIPT_NAME = "empcd"
INITSCRIPT_PARAMS = "start 85 2 3 4 5 . stop 15 0 1 6 ."

CONFFILES_${PN} = "${sysconfdir}/empcd.conf"
