
from flask import Flask, request, jsonify
from transformers import BertTokenizer, BertModel
import torch

app = Flask(__name__)

# Load pre-trained BERT model and tokenizer
tokenizer = BertTokenizer.from_pretrained('bert-base-uncased')
model = BertModel.from_pretrained('bert-base-uncased')

def get_embeddings(text_list):
    embeddings = []
    for text in text_list:
        inputs = tokenizer(text, return_tensors='pt')
        outputs = model(**inputs)
        embeddings.append(outputs.last_hidden_state.mean(dim=1).detach().numpy().tolist())
    return embeddings

@app.route('/embed', methods=['POST'])
def embed():
    print("api requested")
    data = request.json
    if 'texts' not in data:
        return jsonify({'error': 'No texts provided'}), 400
    text_list = data['texts']
    embeddings = get_embeddings(text_list)
    return jsonify({'embeddings': embeddings})

if __name__ == '__main__':
    app.run(host="localhost", port=5000)
