from django.db import models
from django.db.models import Model 
from django.contrib.auth.models import User
import uuid
# Create your models here.

class URLData(models.Model):
    id = models.UUIDField(default=uuid.uuid4, primary_key=True)
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    url = models.TextField()
    created = models.DateTimeField(auto_now_add=True)
