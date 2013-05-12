FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

do_configure_prepend() {
    sed -i -e "s|pcm.front cards.pcm.front|pcm.front cards.pcm.default|" \
        ${S}/src/conf/alsa.conf
}

do_install_append() {
    install -d ${D}${sysconfdir}/modprobe.d
    install -m 0644 ${WORKDIR}/alsa.conf ${D}${sysconfdir}/modprobe.d/
}

FILES_alsa-conf-base += "${sysconfdir}/modprobe.d/*"

