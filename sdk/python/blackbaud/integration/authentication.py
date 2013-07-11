'''
A module for authenticating requests to Blackbaud's thrift integration API.
'''

import time
import base64
import hashlib
import hmac

from blackbaud.integration.generated.types.ttypes import Credential

class CredentialBuilder(object):
    '''
    Builds authenticated Credentials for a particular API user.
    '''

    def __init__(self, apiUserName, sharedSymmetricKey):
        self.apiUserName = apiUserName
        self.sharedSymmetricKey = sharedSymmetricKey

    def sign(self, millisSinceEpoch=None):
        '''
        Returns a new Credential, authenticated for a particular moment in time.
        '''

        if millisSinceEpoch is None:
            # default to 'now'
            millisSinceEpoch = int(round(time.time() * 1000))

        toSignBytes = bytes(self.apiUserName + str(millisSinceEpoch))
        sharedSymmetricKeyBytes = bytes(self.sharedSymmetricKey);
        hmacDigest = hmac.new(sharedSymmetricKeyBytes, toSignBytes, digestmod=hashlib.sha256).digest()
        base64HmacDigest = base64.b64encode(hmacDigest);

        return Credential(self.apiUserName, millisSinceEpoch, base64HmacDigest)
