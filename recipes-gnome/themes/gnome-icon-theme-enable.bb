DESCRIPTION = "Enable gnome-icon-theme in gtkrc"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

RDEPENDS_${PN} = "gnome-icon-theme"
PR = "r3"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "all"

pkg_postinst_${PN}() {
#!/bin/sh
mkdir -p $D${sysconfdir}/gtk-2.0
touch $D${sysconfdir}/gtk-2.0/gtkrc
sed -i /gtk-icon-theme-name/d $D${sysconfdir}/gtk-2.0/gtkrc
echo 'gtk-icon-theme-name = "gnome"' >> $D${sysconfdir}/gtk-2.0/gtkrc
}

