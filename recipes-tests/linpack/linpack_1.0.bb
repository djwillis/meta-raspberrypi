DESCRIPTION = "Linpack benchmark, calculates FLOPS"
SECTION = "test"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
PR = "r0"

SRC_URI = "file://linpack.c"

S = "${WORKDIR}"

do_compile() {
	${CC} -O -o linpack linpack.c -lm
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 linpack ${D}${bindir}
}
