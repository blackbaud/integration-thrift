from thrift import Thrift
from thrift.protocol import TBinaryProtocol
from thrift.transport import TSocket, TTransport
from blackbaud.integration.generated.services import LuminatOnline
from blackbaud.integration.authentication import CredentialBuilder
from blackbaud.integration.generated.types.ttypes import BlackbaudRecordIds,\
  UnboundedDateRange

class LuminateOnlineClient(LuminatOnline.Client):

  def __init__(self):
    self.transport = TTransport.TFramedTransport(TSocket.TSocket('localhost', 9090))
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
    constituentDeliveries = client.getConstituentEmailCommunicationHistory(credBldr.sign(), 
                                                         BlackbaudRecordIds(1000001), 
                                                         UnboundedDateRange())
    #TODO
    print constituentDeliveries
    
    
  except Thrift.TException, tx:
      print '%s' % (tx.message) 
      
  finally:
    client.close() 

if  __name__ =='__main__':main()