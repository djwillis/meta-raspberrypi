include erlang.inc
DEPENDS += "erlang-native openssl"

PR = "r3"

EI_PV="3.7.6"
IC_PV="4.2.29"

SRC_URI += "\
            file://erts-emulator-Makefile.in.patch \
            file://lib-erl_interface-src-Makefile.in.patch \
            "

TARGET_CC_ARCH += "${LDFLAGS}"

EXTRA_OEMAKE = "BUILD_CC='${BUILD_CC}'"

EXTRA_OECONF = "--with-ssl=${STAGING_DIR_HOST}${layout_exec_prefix}"

EXTRA_OECONF_append_arm = " --disable-smp-support --disable-hipe"
EXTRA_OECONF_append_armeb = " --disable-smp-support --disable-hipe"
EXTRA_OECONF_append_mipsel = " --disable-smp-support --disable-hipe"
EXTRA_OECONF_append_sh3 = " --disable-smp-support --disable-hipe"
EXTRA_OECONF_append_sh4 = " --disable-smp-support --disable-hipe"

NATIVE_BIN = "${STAGING_LIBDIR_NATIVE}/erlang/bin"

do_configure() {
    cd ${S}/erts; autoreconf; cd -

    . ${CONFIG_SITE}

    ac_cv_prog_javac_ver_1_2=no \
    ac_cv_prog_javac_ver_1_5=no \
	SHLIB_LD='${CC}' \
    CROSS_COMPILING=yes BOOTSTRAP_ONLY=no oe_runconf

    sed -i -e 's|$(ERL_TOP)/bin/dialyzer|${NATIVE_BIN}/dialyzer --output_plt $@ -pa $(ERL_TOP)/lib/kernel/ebin -pa $(ERL_TOP)/lib/stdlib/ebin|' lib/dialyzer/src/Makefile
}

do_compile() {
    TARGET=${TARGET_SYS} \
    PATH=${NATIVE_BIN}:$PATH \
    oe_runmake noboot
}

do_install() {
    TARGET=${TARGET_SYS} \
    PATH=${NATIVE_BIN}:$PATH \
    oe_runmake 'INSTALL_PREFIX=${D}' install
    for f in erl start
        do sed -i -e 's:ROOTDIR=.*:ROOTDIR=/usr/lib/erlang:' \
        	${D}/usr/lib/erlang/erts-*/bin/$f ${D}/usr/lib/erlang/bin/$f
    done
}

erl_int_path="${libdir}/${PN}/lib/erl_interface-${EI_PV}"
ic_path="${libdir}/${PN}/lib/ic-${IC_PV}"

PACKAGES =+ "${PN}-libs-dbg ${PN}-libs"

FILES_${PN}-staticdev += "${libdir}/*/*/*/*.a ${libdir}/*/*/*/*/*.a ${libdir}/*/*/*/*/*/*.a ${erl_int_path}/lib/*.a ${ic_path}/lib/*.a "
FILES_${PN}-libs-dbg += " ${libdir}/erlang/*/.debug ${libdir}/erlang/*/*/.debug ${libdir}/erlang/*/*/*/.debug ${libdir}/erlang/*/*/*/*/.debug ${libdir}/erlang/*/*/*/*/*/.debug "

SRC_URI[md5sum] = "dd6c2a4807551b4a8a536067bde31d73"
SRC_URI[sha256sum] = "5bc34fc34fc890f84bae7ff1f7c81fbec2c9aa28a0ef51a57d7a8192204d8aa2"

python do_qa_configure() {
    import subprocess

    ###########################################################################
    # Check config.log for cross compile issues
    ###########################################################################

    configs = []
    workdir = d.getVar('WORKDIR', True)
    bb.note("Checking autotools environment for common misconfiguration")
    for root, dirs, files in os.walk(workdir):
        statement = "grep -e 'CROSS COMPILE Badness:' -e 'is unsafe for cross-compilation' %s > /dev/null" % \
                    os.path.join(root,"config.log")
        if "config.log" in files:
            if subprocess.call(statement, shell=True) == 0:
                bb.warn("""This autoconf log indicates errors, it looked at host include and/or library paths while determining system capabilities.
Rerun configure task after fixing this. The path was '%s'""" % root)

        if "configure.ac" in files:
            configs.append(os.path.join(root,"configure.ac"))
        if "configure.in" in files:
            configs.append(os.path.join(root, "configure.in"))

    ###########################################################################
    # Check gettext configuration and dependencies are correct
    ###########################################################################

    cnf = d.getVar('EXTRA_OECONF', True) or ""
    if "gettext" not in d.getVar('P', True) and "gcc-runtime" not in d.getVar('P', True) and "--disable-nls" not in cnf:
        ml = d.getVar("MLPREFIX", True) or ""
        if bb.data.inherits_class('native', d) or bb.data.inherits_class('cross', d) or bb.data.inherits_class('crosssdk', d) or bb.data.inherits_class('nativesdk', d):
            gt = "gettext-native"
        elif bb.data.inherits_class('cross-canadian', d):
            gt = "nativesdk-gettext"
        else:
            gt = "virtual/" + ml + "gettext"
        deps = bb.utils.explode_deps(d.getVar('DEPENDS', True) or "")
        if gt not in deps:
            for config in configs:
                gnu = "grep \"^[[:space:]]*AM_GNU_GETTEXT\" %s >/dev/null" % config
                if subprocess.call(gnu, shell=True) == 0:
                    bb.fatal("""%s required but not in DEPENDS for file %s.
Missing inherit gettext?""" % (gt, config))

    ###########################################################################
    # Check license variables
    ###########################################################################

    if not package_qa_check_license(workdir, d):
        bb.fatal("Licensing Error: LIC_FILES_CHKSUM does not match, please fix")

}

