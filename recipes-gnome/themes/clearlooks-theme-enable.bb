DESCRIPTION = "Enable clearlooks theme in gtkrc"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit allarch
PR = "r1"

RDEPENDS_${PN} = "gtk-theme-clearlooks"

ALLOW_EMPTY_${PN} = "1"

pkg_postinst_${PN}() {
#!/bin/sh
mkdir -p $D${sysconfdir}/gtk-2.0
touch $D${sysconfdir}/gtk-2.0/gtkrc
sed -i /gtk-theme-name/d $D${sysconfdir}/gtk-2.0/gtkrc
echo 'gtk-theme-name = "Clearlooks"' >> $D${sysconfdir}/gtk-2.0/gtkrc
}

