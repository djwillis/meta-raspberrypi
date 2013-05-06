INITSCRIPT_PARAMS = "start 25 2 3 4 5 . stop 22 0 1 6 ."

do_install () {
    # fix the modprobe args and adjust the size down a little
    sed -i -e "s|zram_num_devices|num_devices|" ${WORKDIR}/init
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/zram

    install -d ${D}${sysconfdir}/default
    echo "FACTOR=70" > ${D}${sysconfdir}/default/zram
}

FILES_${PN} += "${sysconfdir}/default/"
