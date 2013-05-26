SECTION = "x11/utils"
DESCRIPTION = "GKrellM is a GTK-based stacked monitor program."
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=6aa4c0c48b808b45244efd507765e2b8"


INC_PR = "r1"

SRC_URI = "http://members.dslextreme.com/users/billw/gkrellm/gkrellm-${PV}.tar.bz2 \
	file://gkrellmd \
	file://gkrellmd.conf \
"

inherit pkgconfig update-rc.d

DEPENDS = "gtk+ glib-2.0 libsm libice"

EXTRA_OEMAKE = "'glib12=0' 'STRIP=/bin/true'"

export LINK_FLAGS = "${LDFLAGS}"
export SMC_LIBS = "-lSM -lICE -lX11 -lgmodule-2.0 -lm"

do_configure_prepend() {
	sed -i -e "s|getline|gkrellm_getline|" ${S}/src/client.c
}

do_install () {
	# fix install errors
	sed -i -e 's|gkrellm \$(INSTALLDIR)/$(PACKAGE|gkrellm \$(INSTALLDIR|' \
			${S}/src/Makefile
	sed -i -e 's|gkrellmd \$(SINSTALLDIR)/\$(PACKAGE_D|gkrellmd \$(SINSTALLDIR|' \
			${S}/server/Makefile

	oe_runmake 'INSTALLDIR=${D}${bindir}' \
		'SINSTALLDIR=${D}${bindir}' \
		'MANDIR=${D}${mandir}/man1' \
		'SMANDIR=${D}${mandir}/man1' \
		'INCLUDEDIR=${D}${includedir}' \
		'PKGCONFIGDIR=${D}${libdir}/pkgconfig' \
		'LOCALEDIR=${D}${datadir}/locale' \
		install

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/gkrellmd ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/gkrellmd.conf ${D}${sysconfdir}

	mkdir -p ${D}${sbindir}
	mv ${D}${bindir}/gkrellmd ${D}${sbindir}/
	find ${D} -name true | xargs rm
}

PACKAGES += "gkrellm-client"
FILES_${PN} = "${sysconfdir}/gkrellmd.conf ${sysconfdir}/init.d/gkrellmd \
${sbindir}"
FILES_${PN}-client = "${bindir}"
CONFFILES_${PN} = "${sysconfdir}/gkrellmd.conf"

INITSCRIPT_NAME = "gkrellmd"
INITSCRIPT_PARAMS = "start 26 2 3 4 5 . stop 21 0 1 6 ."

pkg_postinst_gkrellm() {
if test "x$D" != "x"; then
    exit 1
else
    /etc/init.d/gkrellmd stop
    sleep 1
fi
}
