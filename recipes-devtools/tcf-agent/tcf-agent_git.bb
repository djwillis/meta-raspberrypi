DESCRIPTION = "Target Communication Framework"
HOMEPAGE = "http://wiki.eclipse.org/TCF"
BUGTRACKER = "https://bugs.eclipse.org/bugs/"

LICENSE = "EPL-1.0 | EDL-1.0"
LIC_FILES_CHKSUM = "file://edl-v10.html;md5=522a390a83dc186513f0500543ad3679"

SRCREV = "1.1"
PV = "1.1+git${SRCPV}"
PR = "r0"

SRC_URI = "git://git.eclipse.org/gitroot/tcf/org.eclipse.tcf.agent.git \
           file://tcf-agent.init \
           file://fix_ranlib.patch \
"

DEPENDS = "util-linux openssl"
RDEPENDS_${PN} = "bash"

S = "${WORKDIR}/git/agent"

inherit update-rc.d

INITSCRIPT_NAME = "tcf-agent"
INITSCRIPT_PARAMS = "start 99 3 5 . stop 20 0 1 2 6 ."

# mangling needed for make
MAKE_ARCH = "`echo ${TARGET_ARCH} | sed s,i.86,i686,`"
MAKE_OS = "`echo ${TARGET_OS} | sed s,^linux.*,GNU/Linux,`"

EXTRA_OEMAKE = "MACHINE=${MAKE_ARCH} OPSYS=${MAKE_OS} 'CC=${CC}' 'AR=${AR}'"

do_configure_prepend() {
    if [ "${MACHINE}" = "raspberrypi" ] ; then
        MACH_SUFFIX="l"
    else
        MACH_SUFFIX=""
    fi

    AGENT_ARCH="${ARMPKGARCH}${MACH_SUFFIX}"

    cp -aR ${S}/machine/arm ${S}/machine/${AGENT_ARCH}
}

do_compile() {
    oe_runmake
}

do_install() {
    oe_runmake install INSTALLROOT=${D}
    install -d ${D}${sysconfdir}/init.d/
    install -m 0755 ${WORKDIR}/tcf-agent.init ${D}${sysconfdir}/init.d/tcf-agent
}

