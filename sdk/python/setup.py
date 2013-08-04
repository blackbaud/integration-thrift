#!/usr/bin/env python

from distutils.core import setup

setup(name='Blackbaud Integration Python SDK',
      version='1.0',
      description='''The Python SDK for interacting with Blackbaud's thrift-based services''',
      author='Dave Wingate',
      author_email='david.wingate@blackbaud.com',
      url='https://github.com/blackbaud/integration-thrift',
      packages=['blackbaud', 'blackbaud.integration', 'blackbaud.integration.generated', 'blackbaud.integration.generated.types', 'blackbaud.integration.generated.errors', 'blackbaud.integration.generated.services']
     )
