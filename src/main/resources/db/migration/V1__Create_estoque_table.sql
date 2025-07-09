-- Criação da tabela de estoque simplificada
CREATE TABLE IF NOT EXISTS estoque (
    id BIGSERIAL PRIMARY KEY,
    id_produto UUID NOT NULL,
    quantidade INTEGER NOT NULL DEFAULT 0,
    UNIQUE(id_produto)
);

-- Índice para busca por produto
CREATE INDEX IF NOT EXISTS idx_estoque_id_produto ON estoque(id_produto);

-- Comentários das colunas
COMMENT ON COLUMN estoque.id IS 'Identificador único do registro de estoque';
COMMENT ON COLUMN estoque.id_produto IS 'Identificador UUID do produto';
COMMENT ON COLUMN estoque.quantidade IS 'Quantidade atual em estoque';
