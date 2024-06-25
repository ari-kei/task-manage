# 全モジュールの初期処理
init:
	make auth-init

# Authサーバ初期処理
auth-init: 
	mkdir -p ./auth/crt ;\
	openssl genrsa -out ./auth/crt/privatekey.pem 2048 ;\
	openssl rsa -in ./auth/crt/privatekey.pem -out ./auth/crt/pubkey.pem -outform PEM -pubout;