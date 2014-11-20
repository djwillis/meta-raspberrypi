SUMMARY = "Creates a default 'rpi' user account"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PR = "r3"

SRC_URI = ""

inherit allarch useradd

do_configure() {
    :
}

do_compile() {
    :
}

do_install() {
    :
}

RDEPENDS_${PN} = "base-passwd shadow sudo"

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "--system shutdown"
USERADD_PARAM_${PN} = "--create-home \
                       --groups video,tty,uucp,sudo,audio,games,input,shutdown,disk \
                       --user-group rpi"

ALLOW_EMPTY_${PN} = "1"

pkg_postinst_${PN} () {
# default rpi login is raspberrypi
sed -i -e 's|rpi:!|rpi:$1$moFmu4wA$lP9LgGpGixs25jJMgPGbR/|' $D${sysconfdir}/shadow
}

pkg_postrm_${PN} () {
if test "x$D" != "x"; then
    exit 1
else
    sed -i /^rpi/d ${sysconfdir}/passwd ${sysconfdir}/shadow ${sysconfdir}/group || true
fi
}
