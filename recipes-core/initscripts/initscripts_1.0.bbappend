FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 3}"

SRC_URI += "file://volatiles"

do_install_append() {
    install -d ${D}${sysconfdir}/default/volatiles
    cp -f ${WORKDIR}/volatiles ${D}${sysconfdir}/default/volatiles/00_core
}
