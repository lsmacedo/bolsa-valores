----------BOLSA VALORES----------

Grupo: Lucas Macedo e Pedro Almeida

Para testar a aplica��o, executar os arquivos AppBolsa.cmd e AppBroker.cmd que se encontram nesta pasta.

-- APP BROKER --
O AppBroker fornece um menu de utiliza��o, pelo qual � poss�vel realizar as seguintes opera��es:

1- Comprar:      Fornecendo o nome da a��o, quantidade e o valor desejados, ser� realizada uma ordem de compra.
2- Vender:       Fornecendo o nome da a��o, quantidade e o valor desejados, ser� realizada uma ordem de venda.
3- Informa��es:  Fornecendo o nome da a��o e uma data, ser�o listadas para o cliente todas as opera��es
                 desta a��o realizadas a partir da data informada.
4- Inscrever:    Fornecendo o nome da a��o, a aplica��o corrente passar� a receber todas as ordens de compra, 
                 venda e transa��es referentes � a��o informada.
5- Desinscrever: Fornecendo o nome da a��o, a aplica��o corrente passar� a n�o mais receber ordens de compra,
                 venda e transa��es referentes � a��o informada.

-- APP BOLSA --
O AppBolsa gerencia as opera��es realizadas pelos Brokers. Sempre que uma nova opera��o de compra ou venda
� realizada por algum broker, ela ser� armazenada em uma lista no AppBolsa.
Quando h� alguma opera��o de compra que bate com uma de venda, o AppBolsa realizar� a transa��o, notificar�
os brokers a atualizar� ou remover� as opera��es da lista.
Quando algum broker realizar uma solicita��o de informa��es, ela enviar� uma mensagem com as opera��es de 
compra e venda realizadas a partir da data desejada.