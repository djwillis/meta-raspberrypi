FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

RPROVIDES_${PN} += "imlib2-tests imlib2-themes imlib2-filters imlib2-loaders"

DEBIAN_NOAUTONAME_${PN}-tests = "1"
DEBIAN_NOAUTONAME_${PN}-themes = "1"
DEBIAN_NOAUTONAME_${PN}-filters = "1"
DEBIAN_NOAUTONAME_${PN}-loaders = "1"
DEBIAN_NOAUTONAME_${PN}-filters-dbg = "1"
DEBIAN_NOAUTONAME_${PN}-loaders-dbg = "1"

