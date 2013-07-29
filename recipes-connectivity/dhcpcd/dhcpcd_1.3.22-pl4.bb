SECTION = "console/network"
DESCRIPTION = "dhcpcd is an RFC2131-, RFC2132-, and \
RFC1541-compliant DHCP client daemon. It gets an IP address \
and other information from the DHCP server, automatically \
configures the network interface, and tries to renew the \
lease time according to RFC2131 or RFC1541."

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

PR = "r1"

sbindir = "/sbin"

SRC_URI = "http://www.phystech.com/ftp/dhcpcd-${PV}.tar.gz \
	   file://config_dir.patch \
	   file://paths.patch"

inherit autotools

do_configure_append() {
    # remove the liberally sprinkled strip flags
    sed -i \
        -e "s|STRIP_FLAG=-s|STRIP_FLAG=|" \
        -e "s|-c -s|-c|" \
        -e "s|-s -O2|-O2|" \
        Makefile
}

SRC_URI[md5sum] = "dd627a121e43835bead3ffef5b1a72fd"
SRC_URI[sha256sum] = "f435e14e1f54dc8792f4e655463d07f2bdbe6d8a8581bd62f5167334ab18bb87"
