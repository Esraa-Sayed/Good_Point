from django.db import models
from user_account.models import User


# Create your models here.

class LostObject(models.Model):
    date = models.DateTimeField(null=False)
    city = models.CharField(max_length=35, null=False)
    user_id = models.ForeignKey(User, related_name='lost', on_delete=models.DO_NOTHING, db_column='user_id')


class LostPerson(models.Model):
    id_l = models.OneToOneField(LostObject, on_delete=models.CASCADE, db_column='id_l')
    image = models.BinaryField(unique=True)

    class Meta:
        unique_together = (('id_l', 'image'),)


class LostItem(models.Model):
    id_l = models.OneToOneField(LostObject, primary_key=True, on_delete=models.CASCADE, db_column='id_l')
    type = models.CharField(max_length=20)
    color = models.CharField(max_length=20, null=True)
    brand = models.CharField(max_length=50, null=True)
    description = models.CharField(max_length=700)
    serial_number = models.CharField(max_length=100, null=True)


class FoundObject(models.Model):
    date = models.DateTimeField(null=False)
    longitude = models.DecimalField(max_digits=14, decimal_places=10, default=0.0)
    latitude = models.DecimalField(max_digits=14, decimal_places=10, default=0.0)
    city = models.CharField(max_length=35, null=False)
    is_delivered = models.BooleanField()
    user_id = models.ForeignKey(User, related_name='found', on_delete=models.DO_NOTHING, db_column='user_id')


class FoundPerson(models.Model):
    id_f = models.OneToOneField(LostObject, on_delete=models.CASCADE, db_column='id_f')
    image = models.BinaryField(unique=True)

    class Meta:
        unique_together = (('id_f', 'image'),)


class FoundItem(models.Model):
    id_f = models.OneToOneField(LostObject, primary_key=True, on_delete=models.CASCADE, db_column='id_f')
    type = models.CharField(max_length=20)
    color = models.CharField(max_length=20, null=True)
    brand = models.CharField(max_length=50, null=True)
    description = models.CharField(max_length=700)
    serial_number = models.CharField(max_length=100, null=True)
    candidate = models.ManyToManyField(LostItem, related_name='candidate', db_column='candidate')


class Match(models.Model):
    id_l = models.OneToOneField(LostObject, related_name='lost_matched', on_delete=models.CASCADE, null=False, db_column='id_l')
    id_f = models.OneToOneField(LostObject, related_name='found_matched', on_delete=models.CASCADE, null=False,
                                db_column='id_f')
    date_of_receiving = models.DateTimeField(auto_now_add=True)

    class Meta:
        unique_together = (('id_l', 'id_f'),)
