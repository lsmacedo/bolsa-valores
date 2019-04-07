
# BOLSA VALORES

## Grupo:

 Lucas Macedo e Pedro Almeida
 
## Instruções de execução:

Para testar a aplicação, executar os arquivos AppBolsa.cmd e AppBroker.cmd que se encontram nesta pasta.

## AppBroker
O AppBroker fornece um menu de utilização, pelo qual é possível realizar as seguintes operações:

 1. *Comprar*:  Fornecendo o nome da ação, quantidade e o valor desejados,
    será realizada uma ordem de compra.
 2. *Vender*:  Fornecendo o nome da ação, quantidade e o valor
   desejados, será realizada uma ordem de venda.
 3. *Informações*:  Fornecendo o nome da ação e uma data, serão listadas
   para o cliente todas as operações desta ação realizadas a partir da data informada.
 4. *Inscrever*:    Fornecendo o nome da ação, a aplicação corrente
   passará a receber todas as ordens de compra, venda e transações
   referentes à ação informada.
 5. *Desinscrever*: Fornecendo o nome da ação, a aplicação corrente
   passará a não mais receber ordens de compra, venda e transações
   referentes à ação informada.
   
Obs: O broker somente receberá transações das ações em que ele foi previamente inscrito.

## AppBolsa
O AppBolsa gerencia as operações realizadas pelos Brokers. 
Sempre que uma nova operação de compra ou venda é realizada por algum broker, ela será armazenada em uma lista no AppBolsa.
Quando há alguma operação de compra que bate com uma de venda, o AppBolsa realizará a transação, notificará os brokers a atualizará ou removerá as operações da lista.
Quando algum broker realizar uma solicitação de informações, ela enviará uma mensagem com as operações de compra e venda realizadas a partir da data desejada.
