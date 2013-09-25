FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 6}"

SRC_URI += "file://init"

do_install_append() {
    if ! ${@base_contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        install -d ${D}${sysconfdir}/init.d
        cp -f ${WORKDIR}/init ${D}${sysconfdir}/init.d/networking
    fi
}
