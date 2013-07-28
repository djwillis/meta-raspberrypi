DESCRIPTION = "Command-line (scriptable) Music Player Daemon (mpd) Client"
HOMEPAGE = "http://www.musicpd.org/mpc.shtml"
SECTION = "console/multimedia"

PR = "r2"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "${SOURCEFORGE_MIRROR}/musicpd/mpc-${PV}.tar.bz2 \
           file://next_mpd_playlist.sh"

EXTRA_OECONF = "--with-iconv-libraries=${STAGING_LIBDIR} \
		--with-iconv-includes=${STAGING_INCDIR}"

inherit autotools

do_install_append() {
    install ${WORKDIR}/next_mpd_playlist.sh ${D}${bindir}
    chmod +x ${D}${bindir}/next_mpd_playlist.sh

    install -d ${D}/${localstatedir}/lib/mpd
    touch ${D}/${localstatedir}/lib/mpd/current_playlist_index
    chown mpd:mpd ${D}/${localstatedir}/lib/mpd/current_playlist_index
    chmod ug+w ${D}/${localstatedir}/lib/mpd/current_playlist_index
}

FILES_${PN} += "${localstatedir}/lib/mpd/*"

SRC_URI[md5sum] = "3f642c2c2ad7a57a9be7a03351c8c558"
SRC_URI[sha256sum] = "24e9ef0f0fcfb776cfe802aa2b49469c552018516062c17620cf0af2687911a7"
