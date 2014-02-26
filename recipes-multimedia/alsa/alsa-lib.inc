do_compile_prepend() {
    sed -i -e "s|pcm.front cards.pcm.front|pcm.front cards.pcm.default|" \
        ${S}/src/conf/alsa.conf
}

do_install_append() {
    install -d ${D}${sysconfdir}/modprobe.d
    install -m 0644 ${WORKDIR}/alsa.conf ${D}${sysconfdir}/modprobe.d/
}

FILES_alsa-conf-base += "${sysconfdir}/modprobe.d/*"

