require connman-ui.inc

export PKG_CONFIG_PATH="${STAGING_LIBDIR}/pkgconfig"

do_configure_prepend() {
    autopoint || touch config.rpath
    autoreconf -Wcross --verbose --install --force ${EXTRA_AUTORECONF} $acpaths \
        || bbnote "failed to autoreconf"
}

FILES_${PN} += "/usr/share/connman_ui_gtk/*"

