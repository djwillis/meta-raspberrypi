FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

PACKAGECONFIG = "uuid"

PROVIDES += "ntfs-3g"
RDEPENDS_${PN}-dev = "ntfs-3g"

