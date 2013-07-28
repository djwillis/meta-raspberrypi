FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 4}"

SRC_URI_append = "file://mpd"

inherit update-rc.d

INITSCRIPT_NAME = "mpd"
INITSCRIPT_PARAMS = "defaults 75"

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/mpd ${D}${sysconfdir}/init.d/

    chmod 775 ${D}/${localstatedir}/lib/mpd
    chown mpd:mpd ${D}/${localstatedir}/lib/mpd
    chmod g+w ${D}/${localstatedir}/lib/mpd

    install -d ${D}${sysconfdir}/default/volatiles
    echo "d mpd mpd 0755 ${localstatedir}/run/mpd none" \
        > ${D}${sysconfdir}/default/volatiles/99_mpd
}

FILES_${PN} += "${sysconfdir}/init.d/* /var/run/*"
