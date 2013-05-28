FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 7}"

THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
FILESPATH =. "${@base_set_filespath(["${THISDIR}/${PN}"], d)}:"

SRC_URI = "http://dev.gentoo.org/~hwoarang/distfiles/${P}_p20130215.tar.gz"
SRC_URI += "file://openbox-gnome-session-3.4.9.patch"
SRC_URI += "file://mini_x.session"
SRC_URI += "file://menu.xml"

S="${WORKDIR}"

do_configure_prepend() {
	sed -i \
		-e "s:-O0 -ggdb ::" \
		-e 's/-fno-strict-aliasing//' \
		m4/openbox.m4

	autopoint || touch config.rpath
	autoreconf -Wcross --verbose --install --force || bbnote "failed to autoreconf"
}

do_install_append () {
	install -d ${D}/${sysconfdir}/mini_x
	install -m 0755 ${WORKDIR}/mini_x.session ${D}/${sysconfdir}/mini_x/session

	# add default menu
	cp -f ${WORKDIR}/menu.xml ${D}/${sysconfdir}/xdg/openbox/

	# cleanup
	rm -rf ${D}/usr/share/gnome-session
}

FILES_${PN}-config += "${sysconfdir}/mini_x/*"

SRC_URI[md5sum] = "1ccc090eb34d85a91e83feb994b6eaf9"
SRC_URI[sha256sum] = "59f5f0d626a74141921432eec9131759b5991b63d904f6dfbaef2bb5061f0a3f"

