FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

do_install_append() {
    if ! ${@base_contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        rm -rf ${D}/lib/systemd
    fi
}
