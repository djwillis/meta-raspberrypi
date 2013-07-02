DESCRIPTION = "LIRC is a package that allows you to decode and send infra-red signals of many commonly used remote controls."
DESCRIPTION_append_lirc = " This package contains the lirc daemon, libraries and tools."
DESCRIPTION_append_lirc-x = " This package contains lirc tools for X11."
DESCRIPTION_append_lirc-exec = " This package contains a daemon that runs programs on IR signals."
DESCRIPTION_append_lirc-remotes = " This package contains some config files for remotes."
DESCRIPTION_append_lirc-nslu2example = " This package contains a working config for RC5 remotes and a modified NSLU2."

SECTION = "console/network"
PRIORITY = "optional"
HOMEPAGE = "http://www.lirc.org"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"

DEPENDS = "virtual/kernel virtual/libx11 libxau libsm libice"
DEPENDS_nslu2 = "virtual/kernel lirc-modules"
RDEPENDS_lirc-x = "lirc"
RDEPENDS_lirc-exec = "lirc"
RDEPENDS_lirc-nslu2example = "lirc lirc-exec"
RRECOMMENDS_lirc = "lirc-exec"

PR = "r1"

inherit autotools update-rc.d

SRC_URI_append = " file://lircd.init \
                   file://lircmd.init \
                   file://lircexec.init \
                 "

SRC_URI_append_raspberrypi = " file://lircd.conf_ipazzport \
                               file://lircrc_ipazzport \
                             "

SRC_URI_append_nslu2 = " file://lircd.conf_nslu2 \
                         file://lircrc_nslu2 \
                       "

INITSCRIPT_PACKAGES = "lirc lirc-exec"
INITSCRIPT_NAME = "lircd"
INITSCRIPT_PARAMS = "defaults 20"
INITSCRIPT_NAME_lirc-exec = "lircexec"
INITSCRIPT_PARAMS_lirc-exec = "defaults 21"

require lirc-config.inc

S = "${WORKDIR}/lirc-${PV}"

EXTRA_OEMAKE = 'SUBDIRS="daemons tools"'

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install ${WORKDIR}/lircd.init ${D}${sysconfdir}/init.d/lircd
    install ${WORKDIR}/lircexec.init ${D}${sysconfdir}/init.d/lircexec
    install -d ${D}${datadir}/lirc/
    cp -pPR ${S}/remotes ${D}${datadir}/lirc/
    rm -rf ${D}/dev

    # run is no longer volatile, and /var/run is s symlink
    mkdir -p ${D}/run/lirc/
    mv ${D}/var/run/lirc ${D}/run/lirc/
    rm -rf ${D}/var
}

do_install_append_raspberrypi() {
    install -d ${D}${sysconfdir}/lirc
    install ${WORKDIR}/lircrc_ipazzport ${D}${sysconfdir}/lirc/lircrc
    install ${WORKDIR}/lircd.conf_ipazzport ${D}${sysconfdir}/lirc/lircd.conf
}

do_install_append_nslu2() {
	install -d ${D}${sysconfdir}
	install ${WORKDIR}/lircd.conf_nslu2 ${D}${sysconfdir}/lircd.conf
	install ${WORKDIR}/lircrc_nslu2 ${D}${sysconfdir}/lircrc
}

PACKAGES =+ "lirc-x lirc-exec lirc-remotes"
PACKAGES_prepend_nslu2 = "lirc-nslu2example "

FILES_${PN}-dbg += "${bindir}/.debug ${sbindir}/.debug"
FILES_${PN}-dev += "${libdir}/liblirc_client.so"
FILES_${PN} = "${bindir} ${sbindir} ${libdir} ${sysconfdir} ${exec_prefix}/var"
FILES_lirc-x = "${bindir}/irxevent ${bindir}/xmode2"
FILES_lirc-exec = "${bindir}/irexec ${sysconfdir}/init.d/lircexec"
FILES_lirc-remotes = "${datadir}/lirc/remotes"
FILES_lirc-nslu2example = "${sysconfdir}/lircd.conf ${sysconfdir}/lircrc"
FILES_${PN} += "/run/lirc"

CONFFILES_lirc-nslu2example = "${FILES_lirc-nslu2example}"
