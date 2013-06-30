DESCRIPTION = "Command-line (scriptable) Music Player Daemon (mpd) Client"
HOMEPAGE = "http://www.musicpd.org/mpc.shtml"
SECTION = "console/multimedia"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "${SOURCEFORGE_MIRROR}/musicpd/mpc-${PV}.tar.bz2 \
           file://next_mpd_playlist.sh"

EXTRA_OECONF = "--with-iconv-libraries=${STAGING_LIBDIR} \
		--with-iconv-includes=${STAGING_INCDIR}"

inherit autotools

do_install_append() {
    install ${WORKDIR}/next_mpd_playlist.sh ${D}${bindir}
}

SRC_URI[md5sum] = "48897aeb3a7ee5c64f30e56789f105a8"
SRC_URI[sha256sum] = "7b549ca4af77fc5b0472df1ecd9e76d2f8415258ddcfb63dfa64a55a04e1e590"
