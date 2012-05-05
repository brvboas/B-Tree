/*
 * Bruno Villas Boas da Costa RA: 317527
 * Julio Macumoto             RA: 344915
 * Wagner Takeshi Obara       RA: 317365
 */
package arvore;

public class ArvoreB {

    //Atributos da Classe ArvoreB
    private No raiz; //Atributo do Nó raiz;
    private int ordem; //Ordem da Arvore-B;
    private int nElementos; //Contador para a quantidade de elementos na arvore B;

    //Construtor da Classe ArvoreB
    //Cria um novo nó para a raiz, seta a ordem passada como paâmetro e inicializa
    //o atributo contador de numeros de elementos
    public ArvoreB(int n) {
        this.raiz = new No(n);
        this.ordem = n;
        nElementos = 0;
    }  

    //Getters e Setters dos atributos nElementos,ordem e raiz
    public int getnElementos() {
        return nElementos;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getOrdem() {
        return ordem;
    }

    public No getRaiz() {
        return raiz;
    }

    //Metodo de Inserção na ArvoreB
    //parametros: k - chave a ser inserida
    public void insere(int k) {
        //Verifica se a chave a ser inserida existe
        if (BuscaChave(raiz, k) == null) { //só insere se não houver, para evitar duplicação de chaves
            //verifica se a chave está vazia
            if (raiz.getN() == 0) {
                raiz.getChave().set(0, k);//seta a chave na primeira posição da raiz
                raiz.setN(raiz.getN() + 1);
            } else { //caso nao estaja vazia
                No r = raiz;
                //verifica se a raiz está cheia
                if (r.getN() == ordem - 1) {//há necessidade de dividir a raiz
                    No s = new No(ordem);
                    raiz = s;
                    s.setFolha(false);
                    s.setN(0);
                    s.getFilho().set(0, r);
                    divideNo(s, 0, r);//divide nó
                    insereNoNaoCheio(s, k);//depois de dividir a raiz começa inserindo apartir da raiz

                } else {//caso contrario começa inserindo apartir da raiz
                    insereNoNaoCheio(r, k);
                }
            }
            nElementos++;//incrementa o numero de elemantos na arvore
        }
    }

    //Método de divisão de nó
    //Parâmetros: x - nó Pai, y - nó Filho e i - índice i que indica que y é o i-ésimo filho de x.
    public void divideNo(No x, int i, No y) {
        int t = (int) Math.floor((ordem - 1) / 2);
        //cria nó z
        No z = new No(ordem);
        z.setFolha(y.isFolha());
        z.setN(t);

        //passa as t ultimas chaves de y para z
        for (int j = 0; j < t; j++) {
            if ((ordem - 1) % 2 == 0) {
                z.getChave().set(j, y.getChave().get(j + t));
            } else {
                z.getChave().set(j, y.getChave().get(j + t + 1));
            }
            y.setN(y.getN() - 1);
        }

        //se y nao for folha, pasa os t+1 últimos flhos de y para z
        if (!y.isFolha()) {
            for (int j = 0; j < t + 1; j++) {
                if ((ordem - 1) % 2 == 0) {
                    z.getFilho().set(j, y.getFilho().get(j + t));
                } else {
                    z.getFilho().set(j, y.getFilho().get(j + t + 1));
                }

            }
        }

        y.setN(t);//seta a nova quantidade de chaves de y

        //descola os filhos de x uma posição para a direita
        for (int j = x.getN(); j > i; j--) { 
            x.getFilho().set(j + 1, x.getFilho().get(j));
        }

        x.getFilho().set(i + 1, z);//seta z como filho de x na posição i+1

        //desloca as chaves de x uma posição para a direita, para podermos subir uma chave de y
        for (int j = x.getN(); j > i; j--) {
            x.getChave().set(j, x.getChave().get(j - 1));
        }

        //"sobe" uma chave de y para z
        if ((ordem - 1) % 2 == 0) {
            x.getChave().set(i, y.getChave().get(t - 1));
            y.setN(y.getN() - 1);
            
        } else {
            x.getChave().set(i, y.getChave().get(t));
        }

        //incrementa o numero de chaves de x
        x.setN(x.getN() + 1);

    }

    //Método para inserir uma chave em um nó não cheio
    //Paâmetros: x - nó a ser inserido, k - chave a ser inserida no nó x
    public void insereNoNaoCheio(No x, int k) {
        int i = x.getN() - 1;
        //verifica se x é um nó folha
        if (x.isFolha()) {
            //adquire a posição correta para ser inserido a chave
            while (i >= 0 && k < x.getChave().get(i)) {
                x.getChave().set(i + 1, x.getChave().get(i));
                i--;
            }
            i++;
            x.getChave().set(i, k);//insere a chave na posição i
            x.setN(x.getN() + 1);

        } else {//caso x não for folha
            //adquire a posição correta para ser inserido a chave
            while ((i >= 0 && k < x.getChave().get(i))) {
                i--;
            }
            i++;
            //se o filho i de x estiver cheio, divide o mesmo
            if ((x.getFilho().get(i)).getN() == ordem - 1) {
                divideNo(x, i, x.getFilho().get(i));
                if (k > x.getChave().get(i)) {
                    i++;
                }
            }
            //insere a chave no filho i de x
            insereNoNaoCheio(x.getFilho().get(i), k);
        }
        
    }

    //Método de busca de uma chave, retorna um nó onde a chave buscada se encontra
    //Parâmetros: X - nó por onde começar a busca, k - chave a ser buscada
    public No BuscaChave(No X, int k) {
        int i = 1;
        //procura ate nao estourar o tamanho e ate o valor nao ser maior q o procurado
        while ((i <= X.getN()) && (k > X.getChave().get(i - 1))) { 
            i++;
        }
        //se o tamanho nao tiver excedido e for o valor procurado..
        if ((i <= X.getN()) && (k == X.getChave().get(i - 1))) {
            return X;
        }
        //se nao foi é igual, entao foi o tamanho q excedeu. ai procura no filho se nao for folha
        if (X.isFolha()) { //se o no X for folha
            return null;
        } else {
            return (BuscaChave(X.getFilho().get(i - 1), k));
        }
    }

    //Método de Remoção de uma determinada chave da arvoreB
    public void Remove(int k) {
        //verifica se a chave a ser removida existe
        if (BuscaChave(this.raiz, k) != null) {
            //N é o nó onde se encontra k
            No N = BuscaChave(this.raiz, k);
            int i = 1;

            //adquire a posição correta da chave em N
            while (N.getChave().get(i - 1) < k) {
                i++;
            }

            //se N for folha, remove ela e deve se balancear N
            if (N.isFolha()) {
                for (int j = i + 1; j <= N.getN(); j++) {
                    N.getChave().set(j - 2, N.getChave().get(j - 1));//desloca chaves quando tem mais de uma
                }
                N.setN(N.getN() - 1);
                if (N != this.raiz) {
                    Balanceia_Folha(N);//Balanceia N
                }
            } else {//caso contrário(N nao é folha), substitui a chave por seu antecessor e balanceia a folha onde se encontrava o ancecessor
                No S = Antecessor(this.raiz, k);//S eh onde se encontra o antecessor de k
                int y = S.getChave().get(S.getN() - 1);//y é o antecessor de k
                S.setN(S.getN() - 1);
                N.getChave().set(i - 1, y);//substitui a chave por y
                Balanceia_Folha(S);//balanceia S
            }
            nElementos--;//decrementa o numero de elementos na arvoreB
        }
    }

    //Métode de Balancear um nó folha
    //Parâmetros: F - nó Folha a ser balanceada
    private void Balanceia_Folha(No F) {
        //verifica se F está cheio
        if (F.getN() < Math.floor((ordem - 1) / 2)) {
            No P = getPai(raiz, F);//P é o pai de F
            int j = 1;

            //adquire a posição de F em P
            while (P.getFilho().get(j - 1) != F) {
                j++;
            }

            //verifica se o irmão à esquerda de F não tem chaves para emprestar
            if (j == 1 || (P.getFilho().get(j - 2)).getN() == Math.floor((ordem - 1) / 2)) {
                //verifica se o irmão à direita de F não tem chaves para emprestar
                if (j == P.getN() + 1 || (P.getFilho().get(j).getN() == Math.floor((ordem - 1) / 2))) {
                    Diminui_Altura(F); //nenhum irmão tem chaves para emprestar eh necessario diminuir a altura
                } else {//caso contrario (tem chaves para emprestar) executa Balanceia_Dir_Esq
                    Balanceia_Dir_Esq(P, j - 1, P.getFilho().get(j), F);
                }
            } else {//caso contrario (tem chaves para emprestar) executa Balanceia_Esq_Dir
                Balanceia_Esq_Dir(P, j - 2, P.getFilho().get(j - 2), F);
            }
        }
    }

    //Método para diminuir a altura
    //Parâmetros: X - nó onde vai ser diminuido a altura
    private void Diminui_Altura(No X) {
        int j;
        No P = new No(ordem);

        //verifica se X é a raiz
        if (X == this.raiz) {
            //verifica se X está vazio
            if (X.getN() == 0) {
                this.raiz = X.getFilho().get(0);//o filho o de x passa a ser raiz
                X.getFilho().set(0, null); // desaloca o filho de x
            }
        } else {//caso contrario(X nao é raiz)
            int t = (int) Math.floor((ordem - 1) / 2);
            //verifica se o numero de chaves de X é menor que o permitido
            if (X.getN() < t) {
                P = getPai(raiz, X);//P é pai de X
                j = 1;

                //adquire a posicao correta do filho X em P
                while (P.getFilho().get(j - 1) != X) {
                    j++;
                }

                //junta os nós
                if (j > 1) {
                    Juncao_No(getPai(raiz, X), j - 1);
                } else {
                    Juncao_No(getPai(raiz, X), j);
                }
                Diminui_Altura(getPai(raiz, X));
            }
        }
    }
    
    //Mótodo de Balancear da esquerda para a direita
    //Parâmetros: P - Nó pai, e - indica que Esq é o e-ésimo filho de P, Esq - Nó da esquerda, Dir - Nó da direita
    private void Balanceia_Esq_Dir(No P, int e, No Esq, No Dir) {
        //Desloca as chave de Dir uma posicao para a direita
        for (int i = 0; i < Dir.getN(); i++) {
            Dir.getChave().set(i + 1, Dir.getChave().get(i));
        }

        //Se Dir nao for folha descola seu filhos uma posicao para a direita
        if (!Dir.isFolha()) {//nao foi testado, mas teoricamente funciona
            for (int i = 0; i > Dir.getN(); i++) {
                Dir.getFilho().set(i + 1, Dir.getFilho().get(i));
            }
        }
        Dir.setN(Dir.getN() + 1);//Incrementa a quantidades de chaves em Dir
        Dir.getChave().set(0, P.getChave().get(e));//"desce" uma chave de P para Dir
        P.getChave().set(e, Esq.getChave().get(Esq.getN() - 1));//"sobe" uma chave de Esq para P
        Dir.getFilho().set(0, Esq.getFilho().get(Esq.getN()));//Seta o ultimo filho de Esq como primeiro filho de Dir
        Esq.setN(Esq.getN() - 1);//Decrementa a quantidade de chaves em Esq

    }

    //Método de Balancear da direita para a esquerda
    //Parâmetros: P - Nó pai, e - indica que Dir é o e-ésimo filho de P, Dir - Nó da direita, Esq - Nó da esquerda
    private void Balanceia_Dir_Esq(No P, int e, No Dir, No Esq) {

        Esq.setN(Esq.getN() + 1);//Incrementa a quantidade de chaves em Esq
        Esq.getChave().set(Esq.getN() - 1, P.getChave().get(e));//"desce" uma chave de P para Esq
        P.getChave().set(e, Dir.getChave().get(0));//"sobe" uma chave de Dir para P
        Esq.getFilho().set(Esq.getN(), Dir.getFilho().get(0));//Seta o primeiro filho de Dir como último filho de Esq

        //descola as chaves de Dir uma posição para a esquerda
        for (int j = 1; j < Dir.getN(); j++) {
            Dir.getChave().set(j - 1, Dir.getChave().get(j));
        }

        //se Dir nao for folha, desloca todos os filhos de Dir uma posicao a esquerda
        if (!Dir.isFolha()) {//nao foi testado, mas teoricamente funciona
            for (int i = 1; i < Dir.getN()+1 ; i++) {
                Dir.getFilho().set(i - 1, Dir.getFilho().get(i));
            }
        }

        //descrementa a quantidade de chaves de Dir
        Dir.setN(Dir.getN() - 1);

    }

    //Método para junção do nó
    //Parâmetros: X - No pai, i - posicao do filho de X onde vai ser juntado
    private void Juncao_No(No X, int i) {
        No Y = X.getFilho().get(i - 1); //cria Y
        No Z = X.getFilho().get(i);//cria Z

        int k = Y.getN();
        Y.getChave().set(k, X.getChave().get(i - 1));//"desce" uma chave de X para X

        //descola as de chaves de Z para Y
        for (int j = 1; j <= Z.getN(); j++) {
            Y.getChave().set(j + k, Z.getChave().get(j - 1));
        }

        //se Z nao for folha, descola seus filhos tbm
        if (!Z.isFolha()) {
            for (int j = 1; j <= Z.getN(); j++) {
                Y.getFilho().set(j + k, Z.getFilho().get(j - 1));
            }
        }

        //seta a quantidades de chaves em Y
        Y.setN(Y.getN() + Z.getN() + 1);
        
        X.getFilho().set(i, null);//desaloca o Z setando o filho de X que apontava pra Z pra null

        //descola os filhos e as chaves de X uma para a esquera, para "fechar o buraco"
        for (int j = i; j <= X.getN() - 1; j++) {//ainda nao
            X.getChave().set(j - 1, X.getChave().get(j));
            X.getFilho().set(j, X.getFilho().get(j + 1));
        }

        //decrementa a quantidade de chaves em X
        X.setN(X.getN() - 1);
    }

    //Metodo que retorna o nó onde a chave antecessora de K se encontra
    //Parâmetros: N - Nó onde começa a busca, k - chave a ser buscada
    private No Antecessor(No N, int k) {
        int i = 1;
        while (i <= N.getN() && N.getChave().get(i - 1) < k) {
            i++;
        }
        if (N.isFolha()) {
            return N;
        } else {
            return Antecessor(N.getFilho().get(i - 1), k);
        }
    }

    //Metodo que retorna o nó pai de N
    //Parâmetros: T - Nó onde começa a busca, N - nó que deve se buscar o pai
    private No getPai(No T, No N) {
        if (this.raiz == N) {
            return null;
        }
        for (int j = 0; j <= T.getN(); j++) {
            if (T.getFilho().get(j) == N) {
                return T;
            }
            if (!T.getFilho().get(j).isFolha()) {
                No X = getPai(T.getFilho().get(j), N);
                if (X != null) {
                    return X;
                }
            }
        }
        return null;
    }

    //Método para Limpar a arvoreB.
    //Parâmetros: N - Nó onde se deve iniciar a varredura, ordem - Nova ordem da arvoreB
    public void LimparArvore(No N, int ordem) {

        for (int i = 0; i < N.getN() + 1; i++) {
            if (!N.isFolha()) {
                LimparArvore(N.getFilho().get(i), ordem);
            }
            N.getFilho().set(i, null);
            N.setN(0);
        }

        if (N == this.raiz) {
            this.raiz = new No(ordem);
        }
        nElementos = 0;
    }
}
