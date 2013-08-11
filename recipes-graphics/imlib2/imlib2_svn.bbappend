FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 2}"

RPROVIDES_${PN}-tests += "imlib2-tests"
RPROVIDES_${PN}-themes += "imlib2-themes"
RPROVIDES_${PN}-filters += "imlib2-filters"
RPROVIDES_${PN}-loaders += "imlib2-loaders"

DEBIAN_NOAUTONAME_${PN}-tests = "1"
DEBIAN_NOAUTONAME_${PN}-themes = "1"
DEBIAN_NOAUTONAME_${PN}-filters = "1"
DEBIAN_NOAUTONAME_${PN}-loaders = "1"
DEBIAN_NOAUTONAME_${PN}-filters-dbg = "1"
DEBIAN_NOAUTONAME_${PN}-loaders-dbg = "1"

