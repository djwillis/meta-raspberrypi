DESCRIPTION = "A dynamic programming language and runtime environment, \
with built-in support for concurrency, distribution and fault tolerance"
SECTION = "devel/erlang"
DEPENDS = "ncurses"
PRIORITY = "optional"
LICENSE = "ErlPL-1.1"
LIC_FILES_CHKSUM = "file://EPLICENCE;md5=09f9063ea35bc5bd124df2fda1d9d2c7"

UPSTREAM_VERSION = "${@bb.data.getVar('PV', d, 1).replace('.', '-')}"

SRC_URI = "http://erlang.org/download/otp_src_${UPSTREAM_VERSION}.tar.gz"
S = "${WORKDIR}/otp_src_${UPSTREAM_VERSION}"

PARALLEL_MAKE = ""

inherit autotools
