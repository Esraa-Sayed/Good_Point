from django.db import models
from django.contrib.auth.models import AbstractUser
from rest_framework_simplejwt.tokens import RefreshToken

# Create your models here.


class User(AbstractUser):
    email = models.EmailField(max_length=65, unique=True, db_index=True)
    phone = models.CharField(max_length=20, null=False, unique=True)
    birthdate = models.DateField(null=False)
    city = models.CharField(max_length=35, null=False)
    profile_pic = models.ImageField(blank=True, null=True, upload_to='profileimg/')
    id_card_pic = models.ImageField(blank=True, null=True, upload_to='idcardimg/')

    def __str__(self):
        return self.email

    def tokens(self):
        refresh = RefreshToken.for_user(self)
        return {
            'refresh': str(refresh),
            'access': str(refresh.access_token)
        }

    class Meta:
        db_table = 'user'
