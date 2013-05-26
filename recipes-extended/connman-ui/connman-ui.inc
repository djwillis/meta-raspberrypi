DESCRIPTION = "ConnMan management interface done in GTK+"
DEPENDS = "connman dbus gtk+3 glib-2.0"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

PR = "r0"

inherit autotools pkgconfig

PV = "0.0+git_b838e640"
SRCREV = "b838e640eddb83d296fb6d146ce756066d37c43b"
SRC_URI = "git://github.com/tbursztyka/connman-ui.git"

S = "${WORKDIR}/git/"

