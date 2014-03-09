FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"


do_install_append() {
    sed -i -e '/# %sudo/a %sudo	ALL=(ALL) ALL' \
        ${D}${sysconfdir}/sudoers
}

RDEPENDS_${PN} += " base-passwd shadow "

pkg_postinst_${PN} () {
# default root login is scrambled
sed -i -e 's|root:!|root:$1$FiiAVVnm$MSXkRu4XzdoEbh4QYV0cG1|' $D${sysconfdir}/shadow
}

