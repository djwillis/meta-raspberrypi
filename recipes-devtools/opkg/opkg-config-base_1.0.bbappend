FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 8}"

SRC_URI_append = "file://local-feeds.conf"

do_configure_append() {
    if [ "${CUSTOM_FEED_CONFIG}" = "internal" ] ; then
        bbnote "Using package feed ${CUSTOM_FEED_URL}"
        sed -i \
            -e "s|www.gentoogeek.org|${CUSTOM_FEED_URL}|" \
            ${WORKDIR}/local-feeds.conf || bbnote "sed fail"
    fi

    if ${@bb.utils.contains('TUNE_FEATURES','arm1176jzfs','true','false',d)}; then
        sed -i \
            -e "s|armv6-vfp|arm1176jzfshf-vfp|g" \
            -e "s|rpi-ipk|rpihf-ipk|" \
            ${WORKDIR}/local-feeds.conf || bbnote "sed fail"
    fi
}

do_install_append() {
    install -m 0644 ${WORKDIR}/local-feeds.conf ${D}${sysconfdir}/opkg/
}

#FILES_${PN} += "${sysconfdir}/opkg/local-feeds.conf"
