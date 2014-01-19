# RaspberryPi BSP default providers

PREFERRED_PROVIDER_virtual/kernel = "linux-raspberrypi"
# The default kernel version is the same as used by Raspbian
PREFERRED_VERSION_linux-raspberrypi ?= "3.10.%"

PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg"
PREFERRED_PROVIDER_virtual/egl = "vc-graphics-hardfp"
PREFERRED_PROVIDER_virtual/libgles2 = "vc-graphics-hardfp"

PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"


