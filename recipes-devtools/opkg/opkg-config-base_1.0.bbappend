FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

CUSTOM_FEED_CONFIG ?= "external"

do_install_appemd() {
    install -m 0644  ${WORKDIR}/base-feeds-${CUSTOM_FEED_CONFIG}.conf \
        ${D}${sysconfdir}/opkg/
}
