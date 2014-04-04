FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"


do_install_append() {
    sed -i -e '/# %sudo/a %sudo	ALL=(ALL) ALL' \
        ${D}${sysconfdir}/sudoers
}

RDEPENDS_${PN} += " base-passwd shadow "

