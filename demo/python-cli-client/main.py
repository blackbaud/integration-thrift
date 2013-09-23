from itertools import groupby
from thrift import Thrift
from thrift.protocol import TBinaryProtocol
from thrift.transport.THttpClient import THttpClient
from blackbaud.integration.generated.services import LuminatOnline
from blackbaud.integration.authentication import CredentialBuilder
from blackbaud.integration.generated.types.ttypes import BlackbaudRecordIds,\
  UnboundedDateRange
from string import join

class LuminateOnlineClient(LuminatOnline.Client):

  def __init__(self):
    self.transport = THttpClient('localhost', 9090, '/luminate.binary')
    self.transport.open()
    LuminatOnline.Client.__init__(self, TBinaryProtocol.TBinaryProtocol(self.transport))

  def close(self):
    self.transport.close() 

def main():
  
  credBldr = CredentialBuilder('Guy Noir', '1234')
  
  client = LuminateOnlineClient()
  
  try:
    
    toEcho = u'H\u022Fwd\u0233, T\u0229x.'.encode('utf-8')
    print 'requesting echo of "%s"' % toEcho
    print 'server replied "%s"' % client.echo(credBldr.sign(), toEcho)
    
    print 'requesting a constituent\'s email history:'
    consMsgs = client.getConstituentEmailCommunicationHistory(credBldr.sign(), 
                                                         BlackbaudRecordIds(1000001), 
                                                         UnboundedDateRange())
    
    def campaignForConsMsg(consMsg):
      return consMsg.emailMessage.parentCampaign.campaignName
    
    consMsgsByCampaign = groupby(sorted(consMsgs, key=campaignForConsMsg), key=campaignForConsMsg)
    
    for campaignName, consMsgs in consMsgsByCampaign:
      print '%s\n\t => %s' % (campaignName, join(map(lambda cm: cm.emailMessage.msgName, consMsgs), ", "))
    
    
  except Thrift.TException, tx:
      print '%s' % (tx.message) 
      
  finally:
    client.close() 

if  __name__ =='__main__':main()