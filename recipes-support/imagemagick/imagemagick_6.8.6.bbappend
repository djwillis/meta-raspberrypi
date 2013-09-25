FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
# Don't forget to bump PRINC if you update the extra files.
PRINC := "${@int(PRINC) + 1}"

EXTRA_OECONF = "--program-prefix= --with-x --without-freetype --without-perl --disable-openmp --without-xml --disable-opencl"
