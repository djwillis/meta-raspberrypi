FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

# workaround for bug #85076 (still open)
#   https://bugs.webkit.org/show_bug.cgi?id=85076

EXTRA_OECONF =+ " --disable-jit "
