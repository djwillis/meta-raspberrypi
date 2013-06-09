FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 7}"

THISDIR := "${@os.path.dirname(bb.data.getVar('FILE', d, True))}"
FILESPATH =. "${@base_set_filespath(["${THISDIR}/${PN}"], d)}:"

SRC_URI = "http://dev.gentoo.org/~hwoarang/distfiles/${P}_p20130215.tar.gz;name=source"
SRC_URI += "http://www.gentoogeek.org/files/rpi-backgrounds.tar.gz;name=backgrounds"
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

	# add some rpi images (creative commons share-able)
	install -d ${D}/usr/share/backgrounds
	install ${S}/rpi-backgrounds/* ${D}/usr/share/backgrounds/

	# cleanup
	rm -rf ${D}/usr/share/gnome-session
}

PACKAGES =+ "openbox-backgrounds"

FILES_${PN}-backgrounds = "/usr/share/backgrounds/*"
FILES_${PN}-config += "${sysconfdir}/mini_x/*"


SRC_URI[source.md5sum] = "1ccc090eb34d85a91e83feb994b6eaf9"
SRC_URI[source.sha256sum] = "59f5f0d626a74141921432eec9131759b5991b63d904f6dfbaef2bb5061f0a3f"

SRC_URI[backgrounds.md5sum] = "ab88c26e62df7e5bbb088318a5407149"
SRC_URI[backgrounds.sha256sum] = "f119970a604060a1f7972065e1b2080d1a1db80c2ac5ed33cefc1a061c3f1ce2"
