# RaspberryPi BSP default providers

PREFERRED_PROVIDER_virtual/kernel = "linux-raspberrypi"
PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg"
PREFERRED_PROVIDER_virtual/egl ?= "vc-graphics"
PREFERRED_PROVIDER_virtual/libgles2 ?= "vc-graphics"
PREFERRED_PROVIDER_virtual/libgl ?= "vc-graphics"
PREFERRED_PROVIDER_linux-libc-headers ?= "linux-libc-headers-raspberrypi"
