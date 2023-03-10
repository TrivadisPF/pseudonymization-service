import argparse
import base64
import json
import logging
import random
import sys

#Setup logging
logger = logging.getLogger(__name__)
logging.basicConfig(stream=sys.stderr, level=logging.INFO)

def my_raise(ex):
    sys.tracebacklimit = 0
    raise ex


#Fetch arguments
parser = argparse.ArgumentParser()
parser.add_argument('-k','--keyid')
parser.add_argument('-s','--secret')
args = parser.parse_args()

key_arn = args.keyid if args.keyid \
    else my_raise(ValueError("Please provide the kms key arn via -k, --keyid"))
secret_name = args.secret if args.secret \
    else my_raise(ValueError("Please provide the secret name via -s, --secret "))


# Method to generate encryption key by calling KMS
def generate_key(key_id: str, size: int, ):
    response = kms.generate_data_key(
        KeyId=key_id,
        NumberOfBytes=size
    )
    return base64.b64encode(response['Plaintext']).decode('ascii')


# Method to upload secret string in Secrets Manager
def upload_to_sm(secret_name: str, key_id: str, secret_string: dict):
    secret.update_secret(
        SecretId=secret_name,
        KmsKeyId=key_id,
        SecretString=json.dumps(secret_string)
    )


#Method to randomly select the starting and ending position of the nonce
def nonce_position():
    str_pos = random.randint(0,20)
    end_pos = str_pos + 12
    return str_pos, end_pos


#Main Function
def main():
    try:
        aes_secret = generate_key(key_arn, 32) #256 bit
        logger.info("AES-GCM-SIV secret key generated!")
        hmac_secret = generate_key(key_arn, 8) #64 bit
        logger.info("HMAC-SHA256 secret key generated!")
        str_pos, end_pos = nonce_position()
        logger.info("Nonce starting and end position generated!")
        secret_string = {
            'encodedSecretKey': aes_secret,
            'encodedNonceKey': hmac_secret,
            'nonceStrPos': str_pos,
            'nonceEndPos': end_pos
        }
        upload_to_sm(secret_name, key_arn, secret_string)
        logger.info("Secret String updated successfully!")
    except ClientError:
        logger.exception("Couldn't update secret!")
        raise


if __name__ == "__main__":
    main()
