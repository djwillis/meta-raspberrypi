FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 5}"

SRC_URI += "file://init"

PACKAGECONFIG ??= "connman"
PACKAGECONFIG[connman] = ",,connman"

do_install_append() {
    if ! ${@base_contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        # only needed for connman without systemd
        if ${@base_contains('PACKAGECONFIG', 'connman', 'true', 'false', d)}; then
            install -d ${D}${sysconfdir}/init.d
            cp -f ${WORKDIR}/init ${D}${sysconfdir}/init.d/networking
        fi
    fi
}
